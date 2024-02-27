package com.example.hcltechenviride.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hcltechenviride.EmpLoginActivity
import com.example.hcltechenviride.Models.User
import com.example.hcltechenviride.databinding.FragmentEmpProfileBinding
import com.example.hcltechenviride.utils.EMP_USER_NODE
import com.example.hcltechenviride.utils.EncryptedSharedPrefs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase




class EmpProfileFragment : Fragment() {

    private lateinit var binding: FragmentEmpProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEmpProfileBinding.inflate(inflater, container,false)

        binding.logout.setOnClickListener {
            if (EncryptedSharedPrefs.getCurrentCycleId(requireActivity()) != null || EncryptedSharedPrefs.getCurrentCycleCount(
                    requireActivity()
                ) > 0
            ) {
                    val builder = AlertDialog.Builder(requireContext())

                builder.setTitle("Can't Logout")
                builder.setMessage("Please Return the cycle Before Logout")

                    builder.setPositiveButton("OK") { dialog, which ->

                        dialog.dismiss()
                    }


                    val dialog = builder.create()
                    dialog.show()
                }
                else{
                    FirebaseAuth.getInstance().signOut()
                    EncryptedSharedPrefs.clearAllData(requireActivity())

                    startActivity(Intent(requireActivity(), EmpLoginActivity::class.java))
                    requireActivity().finish()
                }
            }





        return binding.root

    }



    companion object {

    }

    override fun onStart() {
        super.onStart()
        Firebase.firestore.collection(EMP_USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
            .addOnSuccessListener {
                val user:User = it.toObject<User>()!!
                binding.name.text = user.name
                binding.id.text = user.employeeId
                binding.email.text = user.email
                binding.role.text = user.role
            }
    }

    override fun onResume() {
        super.onResume()
    }
}