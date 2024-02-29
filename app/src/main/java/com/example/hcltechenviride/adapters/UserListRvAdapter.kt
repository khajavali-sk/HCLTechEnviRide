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
    inner class ViewHolder(var binding: UserListRvDesignBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = UserListRvDesignBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.root
        val userItem = userList[position]
        holder.binding.employeeId.text = userItem.employeeId
        holder.binding.role.text = userItem.role

    }


}