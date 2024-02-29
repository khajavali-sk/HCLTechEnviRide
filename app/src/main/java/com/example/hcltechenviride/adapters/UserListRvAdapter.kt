package com.example.hcltechenviride.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.hcltechenviride.Models.User
import com.example.hcltechenviride.databinding.UserListRvDesignBinding

class UserListRvAdapter(var context: Context, var userList: ArrayList<User>) :
    RecyclerView.Adapter<UserListRvAdapter.ViewHolder>() {

    // Inner class to hold the views for each list item
    inner class ViewHolder(var binding: UserListRvDesignBinding) :
        RecyclerView.ViewHolder(binding.root)

    // Called when RecyclerView needs a new ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflate the item layout and create a new ViewHolder
        val binding = UserListRvDesignBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
        return ViewHolder(binding)
    }

    // Returns the total number of items in the data set held by the adapter
    override fun getItemCount(): Int {
        return userList.size
    }

    // Called by RecyclerView to display the data at the specified position
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position
        val userItem = userList[position]

        // Set the data to the views in the ViewHolder
        holder.binding.employeeId.text = userItem.employeeId
        holder.binding.role.text = userItem.role
    }
}
