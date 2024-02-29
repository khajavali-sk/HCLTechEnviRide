package com.example.hcltechenviride.fragments_admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.hcltechenviride.Models.History
import com.example.hcltechenviride.adapters.DamagedCycleAdapter
import com.example.hcltechenviride.databinding.FragmentAdminComplaintsBinding
import com.example.hcltechenviride.utils.COMPLAINTS_FOLDER
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class AdminComplaintsFragment : Fragment() {

    private lateinit var binding: FragmentAdminComplaintsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialize adapter and list for damaged cycles
        var adapter: DamagedCycleAdapter
        var damagedCycleList = ArrayList<History>()

        // Inflate the layout for this fragment
        binding = FragmentAdminComplaintsBinding.inflate(inflater, container, false)
        // Set up RecyclerView with StaggeredGridLayoutManager
        binding.rv.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        adapter = DamagedCycleAdapter(requireContext(), damagedCycleList)
        binding.rv.adapter = adapter

        // Retrieve data from Firestore collection
        Firebase.firestore.collection(COMPLAINTS_FOLDER).get().addOnSuccessListener { querySnapshot ->
            val tempList = ArrayList<History>()
            for (document in querySnapshot.documents) {
                // Convert each document to History object and add to list
                val damagedCycle: History = document.toObject<History>()!!
                tempList.add(damagedCycle)
            }
            // Update list and UI with data
            damagedCycleList.addAll(tempList)
            binding.cyCount.text = "Damaged Cycles : ${damagedCycleList.size}"
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }

    companion object {

    }
}
