package com.example.hcltechenviride.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.hcltechenviride.Models.History
import com.example.hcltechenviride.adapters.EmpHistoryRvAdapter
import com.example.hcltechenviride.databinding.FragmentEmpHistoryBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class EmpHistoryFragment : Fragment() {

    private lateinit var binding: FragmentEmpHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEmpHistoryBinding.inflate(inflater, container, false)
        val historyList = ArrayList<History>()
        val adapter = EmpHistoryRvAdapter(requireContext(), historyList)
        binding.rv.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        binding.rv.adapter = adapter

        // Fetch user history from Firestore and populate RecyclerView
        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid)
            .orderBy("duration", Query.Direction.DESCENDING)
            .get().addOnSuccessListener { querySnapshot ->
                val tempList = ArrayList<History>()
                for (document in querySnapshot.documents) {
                    val history: History = document.toObject<History>()!!
                    tempList.add(history)
                }
                historyList.addAll(tempList)
                adapter.notifyDataSetChanged()
            }

        return binding.root
    }
}
