package com.example.hcltechenviride.adapters


import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.hcltechenviride.Models.User
import com.example.hcltechenviride.databinding.UserListRvDesignBinding
import com.example.hcltechenviride.utils.CYCLE_FOLDER
import com.example.hcltechenviride.utils.EMP_USER_NODE
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

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

        holder.binding.delete.setOnClickListener {



            val builder = android.app.AlertDialog.Builder(context)

            builder.setTitle("Confirm Delete?")
            builder.setMessage("Do you want to delete this user ${userItem.employeeId}")

            builder.setNegativeButton("Confirm"){
                    dialog,which -> Firebase.firestore.collection(EMP_USER_NODE)
                .document(Firebase.auth.currentUser?.uid!!).delete()
                removeAt(position)

                dialog.dismiss()
            }


            builder.setPositiveButton("Cancel") { dialog, which ->

                dialog.dismiss()
            }


            val dialog = builder.create()
            dialog.show()
        }
    }

    private fun removeAt(position: Int) {
        userList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, userList.size)
    }
}