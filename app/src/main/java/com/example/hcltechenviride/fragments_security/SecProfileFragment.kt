package com.example.hcltechenviride.fragments_security

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hcltechenviride.EmpLoginActivity
import com.example.hcltechenviride.Models.User
import com.example.hcltechenviride.databinding.FragmentSecProfileBinding
import com.example.hcltechenviride.utils.EMP_USER_NODE
import com.example.hcltechenviride.utils.EncryptedSharedPrefs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase




class SecProfileFragment : Fragment() {

    private lateinit var binding: FragmentSecProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSecProfileBinding.inflate(inflater, container,false)

        binding.logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            EncryptedSharedPrefs.clearAllData(requireActivity())

            startActivity(Intent(requireActivity(),EmpLoginActivity::class.java))
            requireActivity().finish()



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