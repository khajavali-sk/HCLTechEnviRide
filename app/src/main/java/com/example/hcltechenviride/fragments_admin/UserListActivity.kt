package com.example.hcltechenviride.fragments_admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.hcltechenviride.Models.Cycle
import com.example.hcltechenviride.Models.User
import com.example.hcltechenviride.R
import com.example.hcltechenviride.adapters.CycleListRvAdapter
import com.example.hcltechenviride.adapters.UserListRvAdapter
import com.example.hcltechenviride.databinding.ActivityCycleListBinding
import com.example.hcltechenviride.databinding.ActivityUserListBinding
import com.example.hcltechenviride.utils.CYCLE_FOLDER
import com.example.hcltechenviride.utils.EMP_USER_NODE
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class UserListActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityUserListBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        var userList = ArrayList<User>()
        var adapter = UserListRvAdapter(this, userList)
        binding.rv.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        binding.rv.adapter = adapter
        Firebase.firestore.collection(EMP_USER_NODE)
            .orderBy("employeeId", Query.Direction.ASCENDING).get().addOnSuccessListener {
                var tempList = ArrayList<User>()
                for (i in it.documents) {
                    var user: User = i.toObject<User>()!!
                    tempList.add(user)
                }
                userList.addAll(tempList)
                adapter.notifyDataSetChanged()
            }
    }
}