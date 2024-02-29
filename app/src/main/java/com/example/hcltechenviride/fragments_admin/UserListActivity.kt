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

    // Lazily initialize the binding
    private val binding by lazy {
        ActivityUserListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Initialize variables
        val userList = ArrayList<User>()
        val adapter = UserListRvAdapter(this, userList)

        // Set up RecyclerView
        binding.rv.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        binding.rv.adapter = adapter

        // Fetch user data from Firestore
        Firebase.firestore.collection(EMP_USER_NODE)
            .orderBy("role", Query.Direction.ASCENDING).get().addOnSuccessListener { querySnapshot ->
                val tempList = ArrayList<User>()
                // Iterate through the documents
                for (document in querySnapshot.documents) {
                    // Check if the document is not the current user's document
                    if (!document.id.equals(Firebase.auth.currentUser!!.uid)) {
                        // Convert document to User object and add to temporary list
                        val user: User = document.toObject<User>()!!
                        tempList.add(user)
                    }
                }
                // Add all users to the list and notify adapter
                userList.addAll(tempList)
                adapter.notifyDataSetChanged()
            }
    }
}
