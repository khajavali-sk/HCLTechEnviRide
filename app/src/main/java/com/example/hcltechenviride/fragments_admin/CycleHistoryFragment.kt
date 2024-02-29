package com.example.hcltechenviride.fragments_admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.hcltechenviride.Models.History
import com.example.hcltechenviride.adapters.AdminCycleHistoryRvAdapter
import com.example.hcltechenviride.databinding.FragmentCycleHistoryBinding
import com.example.hcltechenviride.utils.HISTORY_FOLDER
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class CycleHistoryFragment : Fragment() {
    private lateinit var binding: FragmentCycleHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCycleHistoryBinding.inflate(inflater, container, false)

        // Initialize history list and adapter
        var historyList = ArrayList<History>()
        var adapter = AdminCycleHistoryRvAdapter(requireContext(), historyList)

        // Set layout manager and adapter to RecyclerView
        binding.rv.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        binding.rv.adapter = adapter

        // Retrieve cycle history data from Firestore and populate the list
        Firebase.firestore.collection(HISTORY_FOLDER)
            .orderBy("duration", Query.Direction.DESCENDING).get().addOnSuccessListener {
                var tempList = ArrayList<History>()
                for (i in it.documents) {
                    var history: History = i.toObject<History>()!! // Convert Firestore document to History object
                    tempList.add(history)
                }
                historyList.addAll(tempList) // Add retrieved history items to the list
                adapter.notifyDataSetChanged() // Notify adapter about data change
            }

        return binding.root
    }
}
