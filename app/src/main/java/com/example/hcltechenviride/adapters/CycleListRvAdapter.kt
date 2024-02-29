package com.example.hcltechenviride.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.hcltechenviride.Models.Cycle
import com.example.hcltechenviride.databinding.CycleListRvDesignBinding
import com.example.hcltechenviride.utils.CYCLE_FOLDER
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CycleListRvAdapter(var context: Context, var cycleList: ArrayList<Cycle>) :
    RecyclerView.Adapter<CycleListRvAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: CycleListRvDesignBinding) :
        RecyclerView.ViewHolder(binding.root)

    // Inflate the layout and create ViewHolder instances
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CycleListRvDesignBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    // Return the size of the cycleList
    override fun getItemCount(): Int {
        return cycleList.size
    }

    // Bind data to views in the ViewHolder
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.root
        val cycleItem = cycleList[position]
        holder.binding.cycleID.text = cycleItem.cycleID
        holder.binding.colorAndLocation.text = "${cycleItem.color} - ${cycleItem.location}"

        // Set up click listener for the delete button
        holder.binding.delete.setOnClickListener {
            // Check if the cycle is currently allotted
            if (cycleList[position].allotted == "True") {
                // Display a toast message if the cycle is in use
                Toast.makeText(context, "Can't Delete Cycle is in use", Toast.LENGTH_SHORT).show()
            } else {
                // If the cycle is not in use, prompt the user for confirmation
                val builder = android.app.AlertDialog.Builder(context)
                builder.setTitle("Confirm Delete!")
                    .setMessage("Do you want to delete this Cycle")
                    // If confirmed, delete the cycle document from Firestore and remove it from the list
                    .setNegativeButton("Confirm") { dialog, which ->
                        Firebase.firestore.collection(CYCLE_FOLDER)
                            .document(cycleList[position].cycleID!!).delete()
                        removeAt(position)
                        dialog.dismiss()
                    }
                    // If canceled, dismiss the dialog
                    .setPositiveButton("Cancel") { dialog, which ->
                        dialog.dismiss()
                    }
                val dialog = builder.create()
                dialog.show()
            }
        }
    }

    // Remove an item at the specified position from the cycleList
    private fun removeAt(position: Int) {
        cycleList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, cycleList.size)
    }
}
