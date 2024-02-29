package com.example.hcltechenviride.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.hcltechenviride.Models.History
import com.example.hcltechenviride.databinding.DamagedCycleListRvDesignBinding
import com.example.hcltechenviride.utils.COMPLAINTS_FOLDER
import com.example.hcltechenviride.utils.CYCLE_FOLDER
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DamagedCycleAdapter(var context: Context, var damagedCycleList: ArrayList<History>) :
    RecyclerView.Adapter<DamagedCycleAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: DamagedCycleListRvDesignBinding) :
        RecyclerView.ViewHolder(binding.root)

    // Create ViewHolder instances
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DamagedCycleAdapter.ViewHolder {
        val binding = DamagedCycleListRvDesignBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    // Return the size of the damagedCycleList
    override fun getItemCount(): Int {
        return damagedCycleList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DamagedCycleAdapter.ViewHolder, position: Int) {
        // Bind data to views in the ViewHolder
        holder.binding.cycleID.text = damagedCycleList[position].cycleID
        holder.binding.employeeId.text = damagedCycleList[position].empID

        // Set up action for the 'repaired' button
        holder.binding.repaired.setOnClickListener {
            // Update Firestore to mark the cycle as repaired and delete its complaint document
            Firebase.firestore.collection(CYCLE_FOLDER).document(damagedCycleList[position].cycleID!!)
                .update("damaged", "False")
            Firebase.firestore.collection(COMPLAINTS_FOLDER).document(damagedCycleList[position].cycleID!!)
                .delete()
            // Remove the repaired cycle from the list and notify the adapter
            removeAt(position)
        }
    }

    // Remove an item at the specified position from the damagedCycleList
    private fun removeAt(position: Int) {
        damagedCycleList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, damagedCycleList.size)
    }
}
