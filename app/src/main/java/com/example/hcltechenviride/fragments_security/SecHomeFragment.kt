package com.example.hcltechenviride.fragments_security

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.hcltechenviride.Models.History
import com.example.hcltechenviride.adapters.ReturnedCycleAdapter
import com.example.hcltechenviride.databinding.FragmentSecHomeBinding
import com.example.hcltechenviride.utils.RETURNED_CYCLE_FOLDER
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class SecHomeFragment : Fragment() {

    private lateinit var binding: FragmentSecHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialize variables
        var adapter: ReturnedCycleAdapter
        var returnedCycleList = ArrayList<History>()

        // Inflate the layout for this fragment
        binding = FragmentSecHomeBinding.inflate(inflater, container, false)
        binding.rv.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        adapter = ReturnedCycleAdapter(requireContext(), returnedCycleList)
        binding.rv.adapter = adapter

        // Fetch data from Firestore
        Firebase.firestore.collection(RETURNED_CYCLE_FOLDER).get().addOnSuccessListener { querySnapshot ->
            val tempList = ArrayList<History>()
            // Iterate through the documents
            for (document in querySnapshot.documents) {
                // Convert each document to a History object and add it to the list
                val returnedCycle: History = document.toObject<History>()!!
                tempList.add(returnedCycle)
            }
            returnedCycleList.addAll(tempList)

            // Update the UI with the count of pending cycles
            binding.cyCount.text = "Pending Cycles : ${returnedCycleList.size}"
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }
}
