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
        // Inflate the layout for this fragment
        binding = FragmentCyclesInUseBinding.inflate(inflater, container, false)

        // Initialize variables
        val cycleInUseList = ArrayList<CurrentCycle>()
        val adapter = AdminCycleInUseRvAdapter(requireContext(), cycleInUseList)

        // Set up RecyclerView
        binding.rv.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        binding.rv.adapter = adapter

        // Fetch current cycles in use from Firestore
        Firebase.firestore.collection(CURRENT_CYCLE_FOLDER)
            .orderBy("allottedTime", Query.Direction.DESCENDING).get().addOnSuccessListener { querySnapshot ->
                val tempList = ArrayList<CurrentCycle>()
                // Iterate through the documents
                for (document in querySnapshot.documents) {
                    // Convert document to CurrentCycle object and add to temporary list
                    val cycle: CurrentCycle = document.toObject<CurrentCycle>()!!
                    tempList.add(cycle)
                }

                // Add all cycles to the list
                cycleInUseList.addAll(tempList)
                // Hide text view if there are cycles in use
                if (cycleInUseList.isNotEmpty()) {
                    binding.textView.visibility = View.INVISIBLE
                }
                // Notify adapter of data changes
                adapter.notifyDataSetChanged()
            }

        return binding.root
    }

    companion object {
        // Any companion object members can be added here if needed
    }
}
