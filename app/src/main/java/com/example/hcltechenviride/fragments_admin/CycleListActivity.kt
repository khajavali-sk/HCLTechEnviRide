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
    private val binding by lazy {
        ActivityCycleListBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        var cycleList = ArrayList<Cycle>()
        var adapter = CycleListRvAdapter(this, cycleList)
        binding.rv.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        binding.rv.adapter = adapter
        Firebase.firestore.collection(CYCLE_FOLDER)
            .orderBy("cycleID", Query.Direction.ASCENDING).get().addOnSuccessListener {
                var tempList = ArrayList<Cycle>()
                for (i in it.documents) {
                    var cycle: Cycle = i.toObject<Cycle>()!!
                    tempList.add(cycle)
                }
                cycleList.addAll(tempList)
                adapter.notifyDataSetChanged()
            }
    }
}