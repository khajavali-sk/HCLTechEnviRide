package com.example.hcltechenviride.fragments_admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.hcltechenviride.Models.User
import com.example.hcltechenviride.adapters.UserListRvAdapter
import com.example.hcltechenviride.databinding.ActivityUserListBinding
import com.example.hcltechenviride.utils.EMP_USER_NODE
import com.google.firebase.auth.ktx.auth
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
            .orderBy("role", Query.Direction.ASCENDING).get().addOnSuccessListener {
                var tempList = ArrayList<User>()
                for (i in it.documents) {
                    if (i.id.toString().equals(Firebase.auth.currentUser!!.uid.toString()))
                    else {
                        var user: User = i.toObject<User>()!!
                        tempList.add(user)
                    }
                }
                userList.addAll(tempList)
                adapter.notifyDataSetChanged()
            }
    }
}