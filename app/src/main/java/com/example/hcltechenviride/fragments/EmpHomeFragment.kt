package com.example.hcltechenviride.fragments

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
import com.example.hcltechenviride.databinding.FragmentEmpHomeBinding
import com.example.hcltechenviride.utils.CURRENT_CYCLE_FOLDER
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.zxing.integration.android.IntentIntegrator

class EmpHomeFragment : Fragment() {
    private lateinit var binding: FragmentEmpHomeBinding

    private var cycleCount = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEmpHomeBinding.inflate(inflater, container, false)

        binding.scan.setOnClickListener {
            if (cycleCount < 1) {

                startQrCodeScanner()
                cycleCount = 1
            } else {
                Toast.makeText(
                    requireActivity(), "can only request one cycle at a time", Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.returnBack.setOnClickListener {
            cycleCount = 0
            Toast.makeText(requireActivity(),"returned successfully",Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startQrCodeScanner() {
        val integrator = IntentIntegrator.forSupportFragment(this)
        integrator.setOrientationLocked(false) // Allow both portrait and landscape scanning
        integrator.setPrompt("Scan QR code containing cycle ID")
        integrator.initiateScan()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                // Scan cancelled or failed
                Log.d("EmpHomeFragment", "Scan cancelled or failed")
                Toast.makeText(requireActivity(), "Scan cancelled or failed", Toast.LENGTH_SHORT)
                    .show()
            } else {
                // QR code scanned successfully
                val scannedCycleId = result.contents
                Log.d("EmpHomeFragment", "Scanned cycle ID: $scannedCycleId")

                // Create history object with scanned ID (modify if needed)
                val currentCycle = CurrentCycle(cycleID = scannedCycleId)

                // Update history on Firestore
                Firebase.firestore.collection(CURRENT_CYCLE_FOLDER).add(currentCycle).addOnSuccessListener {
                    Firebase.firestore.collection(Firebase.auth.currentUser!!.uid)
                        .document().set(currentCycle).addOnSuccessListener {

                            Toast.makeText(
                                requireActivity(), "Cycle allocated successfully", Toast.LENGTH_SHORT
                            ).show()
                        }.addOnFailureListener {
                        Log.e(
                            "EmpHomeFragment", "Error adding history: ${it.localizedMessage}"
                        )
                        cycleCount = 0
                        Toast.makeText(
                            requireActivity(),
                            "Failed to allocate cycle: ${it.localizedMessage}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    //increament cycleCount
                }.addOnFailureListener {
                    Log.e(
                        "EmpHomeFragment", "Error adding history: ${it.localizedMessage}"
                    )
                    cycleCount = 0
                    Toast.makeText(
                        requireActivity(),
                        "Failed to allocate cycle: ${it.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            cycleCount = 0
            Log.e("EmpHomeFragment", "Failed to parse activity result")
            Toast.makeText(requireActivity(), "Failed to parse activity result", Toast.LENGTH_SHORT)
                .show()
        }
    }
}