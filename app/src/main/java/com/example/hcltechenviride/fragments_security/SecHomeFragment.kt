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
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var adapter: ReturnedCycleAdapter
        var returnedCycleList = ArrayList<History>()
        // Inflate the layout for this fragment
        binding = FragmentSecHomeBinding.inflate(inflater, container, false)
        binding.rv.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        adapter = ReturnedCycleAdapter(requireContext(),returnedCycleList)
        binding.rv.adapter = adapter

        Firebase.firestore.collection(RETURNED_CYCLE_FOLDER).get().addOnSuccessListener {
            var tempList = ArrayList<History>()
            for (i in it.documents){
                var returnedCycle: History = i.toObject<History>()!!
                tempList.add(returnedCycle)
            }
            returnedCycleList.addAll(tempList)

            binding.cyCount.text = "Pending Cycles : ${returnedCycleList.size}"
            adapter.notifyDataSetChanged()
        }




        return binding.root
    }

    companion object {

    }
}