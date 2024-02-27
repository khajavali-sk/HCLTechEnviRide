package com.example.hcltechenviride.fragments

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.hcltechenviride.Models.CurrentCycle
import com.example.hcltechenviride.Models.History
import com.example.hcltechenviride.databinding.FragmentEmpHomeBinding
import com.example.hcltechenviride.utils.CURRENT_CYCLE_FOLDER
import com.example.hcltechenviride.utils.CYCLE_FOLDER
import com.example.hcltechenviride.utils.EncryptedSharedPrefs
import com.example.hcltechenviride.utils.HISTORY_FOLDER
import com.example.hcltechenviride.utils.RETURNED_CYCLE_FOLDER
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.google.zxing.integration.android.IntentIntegrator

private const val i = 0

class EmpHomeFragment : Fragment() {
    private lateinit var binding: FragmentEmpHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEmpHomeBinding.inflate(inflater, container, false)

        binding.scan.setOnClickListener {
            if (EncryptedSharedPrefs.getCurrentCycleCount(requireActivity()) < 1) {
                startQrCodeScanner()
                EncryptedSharedPrefs.setCurrentCycleCount(requireActivity(), 1)
            } else {
                Toast.makeText(
                    requireActivity(), "Can only request one cycle at a time", Toast.LENGTH_SHORT
                ).show()
            }
        }


        binding.returnBack.setOnClickListener {
            if (EncryptedSharedPrefs.getCurrentCycleId(requireActivity())!=null|| EncryptedSharedPrefs.getCurrentCycleCount(requireActivity())>0)  {
                returnCurrentCycle(EncryptedSharedPrefs.getCurrentCycleId(requireActivity())!!)
                EncryptedSharedPrefs.setCurrentCycleCount(requireActivity(), 0)
                EncryptedSharedPrefs.clearCurrentCycleId(requireActivity())
                Toast.makeText(requireActivity(), "Returned successfully", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(
                    requireActivity(), "Don't have a cycle to return", Toast.LENGTH_SHORT
                ).show()
            }
        }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startQrCodeScanner() {
        val integrator = IntentIntegrator.forSupportFragment(this)
        integrator.setOrientationLocked(true) // Set orientation to portrait
        integrator.setPrompt("Scan QR code containing cycle ID")
        integrator.initiateScan()
    }

    @Deprecated("Deprecated in Java")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                // Scan cancelled or failed
                EncryptedSharedPrefs.setCurrentCycleCount(requireActivity(), 0)
                Log.d(TAG, "Scan cancelled or failed")
                Toast.makeText(requireActivity(), "Scan cancelled or failed", Toast.LENGTH_SHORT).show()
            } else {
                // QR code scanned successfully
                val scannedCycleId = result.contents
                Log.d(TAG, "Scanned cycle ID: $scannedCycleId")

                val checker = DocumentChecker()
                checker.checkDocument(CYCLE_FOLDER, scannedCycleId) { documentExists, isAllotted, isDamaged ->
                    if (documentExists) {
                        if (isAllotted == "False" && isDamaged == "False") {
                            allotCycle(
                                scannedCycleId,
                                EncryptedSharedPrefs.getCurrentEmployeeId(requireActivity())!!
                            )
                            EncryptedSharedPrefs.setCurrentCycleId(requireActivity(), scannedCycleId)
                        } else {
                            EncryptedSharedPrefs.setCurrentCycleCount(requireActivity(), 0)
                            Toast.makeText(
                                requireActivity(), "Cycle already in use or Damaged", Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        EncryptedSharedPrefs.setCurrentCycleCount(requireActivity(), 0)
                        Toast.makeText(
                            requireActivity(), "Invalid QR Code", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        } else {
            EncryptedSharedPrefs.setCurrentCycleCount(requireActivity(), 0)
            Log.e(TAG, "Failed to parse activity result")
            Toast.makeText(requireActivity(), "Failed to parse activity result", Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun allotCycle(scannedCycleId: String, empId: String) {
        val currentCycle = CurrentCycle(scannedCycleId, empId)

        val db = FirebaseFirestore.getInstance()
        db.collection(CURRENT_CYCLE_FOLDER).document(scannedCycleId).set(currentCycle)
            .addOnSuccessListener {
                db.collection(CYCLE_FOLDER).document(scannedCycleId).update("allotted", "True")
                    .addOnSuccessListener {
                        Toast.makeText(
                            requireActivity(), "Cycle allocated successfully", Toast.LENGTH_SHORT
                        ).show()
                    }.addOnFailureListener {
                        Log.e(TAG, "Error adding history: ${it.localizedMessage}")
                        EncryptedSharedPrefs.setCurrentCycleCount(requireActivity(), 0)
                        Toast.makeText(
                            requireActivity(),
                            "Failed to allocate cycle: ${it.localizedMessage}", Toast.LENGTH_SHORT
                        ).show()
                    }
            }.addOnFailureListener {
                Log.e(TAG, "Error adding history: ${it.localizedMessage}")
                EncryptedSharedPrefs.setCurrentCycleCount(requireActivity(), 0)
                Toast.makeText(
                    requireActivity(), "Failed to allocate cycle: ${it.localizedMessage}", Toast.LENGTH_SHORT
                ).show()
            }
    }




    @RequiresApi(Build.VERSION_CODES.O)
    fun returnCurrentCycle(currCyId: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection(CYCLE_FOLDER).document(currCyId).update("allotted", "False")
            .addOnSuccessListener {
                db.collection(CURRENT_CYCLE_FOLDER).document(currCyId).get()
                    .addOnSuccessListener { documentSnapshot ->
                        val currCycle: CurrentCycle = documentSnapshot.toObject<CurrentCycle>()!!
                        val history: History =
                            History(currCycle.cycleID, currCycle.empID, currCycle.allottedTime)
                        db.collection(HISTORY_FOLDER).document().set(history)
                        db.collection(RETURNED_CYCLE_FOLDER).document(currCyId).set(history)
                        db.collection(Firebase.auth.currentUser!!.uid).document().set(history)
                    }.addOnSuccessListener {
                        db.collection(CURRENT_CYCLE_FOLDER).document(currCyId).delete()
                    }
            }.addOnSuccessListener {
                Log.d(TAG, "Cycle returned Successfully")
            }
    }

    companion object {

    }

}

class DocumentChecker {
    fun checkDocument(
        collectionPath: String, documentName: String, onComplete: (Boolean, Any?, Any?) -> Unit
    ) {
        val db = FirebaseFirestore.getInstance()
        val collectionRef = db.collection(collectionPath)

        collectionRef.document(documentName).get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                val allotted = documentSnapshot.data?.get("allotted")
                val damaged = documentSnapshot.data?.get("damaged")
                onComplete(true, allotted, damaged)
            } else {
                onComplete(false, null, null)
            }
        }.addOnFailureListener { exception ->
            onComplete(false, null, null)
        }
    }
}


