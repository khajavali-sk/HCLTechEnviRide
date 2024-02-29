package com.example.hcltechenviride.fragments_admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.hcltechenviride.Models.Cycle
import com.example.hcltechenviride.adapters.CycleListRvAdapter
import com.example.hcltechenviride.databinding.ActivityCycleListBinding
import com.example.hcltechenviride.utils.CYCLE_FOLDER
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class CycleListActivity : AppCompatActivity() {
    // Binding variable using lazy initialization
    private val binding by lazy {
        ActivityCycleListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Initialize cycle list and adapter
        var cycleList = ArrayList<Cycle>()
        var adapter = CycleListRvAdapter(this, cycleList)

        // Set layout manager and adapter to RecyclerView
        binding.rv.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        binding.rv.adapter = adapter

        // Retrieve cycle data from Firestore and populate the list
        Firebase.firestore.collection(CYCLE_FOLDER)
            .orderBy("cycleID", Query.Direction.ASCENDING).get().addOnSuccessListener {
                var tempList = ArrayList<Cycle>()
                for (i in it.documents) {
                    var cycle: Cycle = i.toObject<Cycle>()!! // Convert Firestore document to Cycle object
                    tempList.add(cycle)
                }
                cycleList.addAll(tempList) // Add retrieved cycles to the list
                adapter.notifyDataSetChanged() // Notify adapter about data change
            }
    }
}
