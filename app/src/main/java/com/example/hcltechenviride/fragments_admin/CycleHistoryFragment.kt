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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCycleHistoryBinding.inflate(inflater, container, false)
        var historyList = ArrayList<History>()
        var adapter = AdminCycleHistoryRvAdapter(requireContext(), historyList)
        binding.rv.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        binding.rv.adapter = adapter
        Firebase.firestore.collection(HISTORY_FOLDER)
            .orderBy("duration", Query.Direction.DESCENDING).get().addOnSuccessListener {
                var tempList = ArrayList<History>()
                for (i in it.documents) {
                    var history: History = i.toObject<History>()!!
                    tempList.add(history)
                }
                historyList.addAll(tempList)
                adapter.notifyDataSetChanged()
            }

        return binding.root
    }
    companion object {

    }


}