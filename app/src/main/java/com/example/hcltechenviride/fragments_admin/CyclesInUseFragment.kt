package com.example.hcltechenviride.fragments_admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.hcltechenviride.Models.CurrentCycle
import com.example.hcltechenviride.adapters.AdminCycleInUseRvAdapter
import com.example.hcltechenviride.databinding.FragmentCyclesInUseBinding
import com.example.hcltechenviride.utils.CURRENT_CYCLE_FOLDER
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase


class CyclesInUseFragment : Fragment() {

    private lateinit var binding: FragmentCyclesInUseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCyclesInUseBinding.inflate(inflater, container, false)
        var cycleInUseList = ArrayList<CurrentCycle>()
        var adapter = AdminCycleInUseRvAdapter(requireContext(), cycleInUseList)
        binding.rv.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        binding.rv.adapter = adapter
        Firebase.firestore.collection(CURRENT_CYCLE_FOLDER)
            .orderBy("allottedTime", Query.Direction.DESCENDING).get().addOnSuccessListener {
                var tempList = ArrayList<CurrentCycle>()
                for (i in it.documents) {
                    var cycle: CurrentCycle = i.toObject<CurrentCycle>()!!
                    tempList.add(cycle)
                }

                cycleInUseList.addAll(tempList)
                if (cycleInUseList.size > 0){
                    binding.textView.visibility = View.INVISIBLE
                }
                adapter.notifyDataSetChanged()
            }

        return binding.root
    }

    companion object {

    }
}