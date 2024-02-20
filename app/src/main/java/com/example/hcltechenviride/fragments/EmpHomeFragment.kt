package com.example.hcltechenviride.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.hcltechenviride.Models.History
import com.example.hcltechenviride.databinding.FragmentEmpHomeBinding
import com.example.hcltechenviride.utils.HISTORY_FOLDER
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class EmpHomeFragment : Fragment() {
    private lateinit var binding: FragmentEmpHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEmpHomeBinding.inflate(inflater, container, false)

        binding.scan.setOnClickListener {
            val history: History = History()
            val addOnSuccessListener =
                Firebase.firestore.collection(HISTORY_FOLDER).document().set(history)
                    .addOnSuccessListener {
                        Toast.makeText(
                            requireActivity(),
                            "cycle allotted Successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
        }


        return binding.root
    }

    companion object {

    }
}