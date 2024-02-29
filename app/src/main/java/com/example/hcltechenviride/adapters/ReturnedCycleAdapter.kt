package com.example.hcltechenviride.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hcltechenviride.Models.History
import com.example.hcltechenviride.databinding.ReturnRvBinding
import com.example.hcltechenviride.utils.COMPLAINTS_FOLDER
import com.example.hcltechenviride.utils.CYCLE_FOLDER
import com.example.hcltechenviride.utils.RETURNED_CYCLE_FOLDER
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ReturnedCycleAdapter(var context: Context, var returnedCycleList: ArrayList<History>) :
    RecyclerView.Adapter<ReturnedCycleAdapter.ViewHolder>() {

    // Inner class to hold the views for each list item
    inner class ViewHolder(var binding: ReturnRvBinding) : RecyclerView.ViewHolder(binding.root)

    // Called when RecyclerView needs a new ViewHolder
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReturnedCycleAdapter.ViewHolder {
        // Inflate the item layout and create a new ViewHolder
        val binding = ReturnRvBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    // Returns the total number of items in the data set held by the adapter
    override fun getItemCount(): Int {
        return returnedCycleList.size
    }

    // Called by RecyclerView to display the data at the specified position
    override fun onBindViewHolder(holder: ReturnedCycleAdapter.ViewHolder, position: Int) {
        // Get the data model based on position
        val historyItem = returnedCycleList[position]

        // Bind data to the views in the ViewHolder
        holder.binding.cycleID.text = historyItem.cycleID
        holder.binding.duration.text = historyItem.duration

        // Set click listeners for complaint and accept buttons
        holder.binding.complain.setOnClickListener {
            // Handle complaint by adding to complaints collection, marking cycle as damaged,
            // and removing from returned cycles collection
            Firebase.firestore.collection(COMPLAINTS_FOLDER)
                .document(historyItem.cycleID!!)
                .set(historyItem)
            Firebase.firestore.collection(CYCLE_FOLDER)
                .document(historyItem.cycleID!!).update("damaged", "True")
            Firebase.firestore.collection(RETURNED_CYCLE_FOLDER)
                .document(historyItem.cycleID!!).delete()
            removeAt(position)
        }

        holder.binding.accept.setOnClickListener {
            // Accept return by removing from returned cycles collection
            Firebase.firestore.collection(RETURNED_CYCLE_FOLDER)
                .document(historyItem.cycleID!!).delete()
            removeAt(position)
        }
    }

    // Removes an item from the list and notifies the adapter about the change
    private fun removeAt(position: Int) {
        returnedCycleList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, returnedCycleList.size)
    }
}
