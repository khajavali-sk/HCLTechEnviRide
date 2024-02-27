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

    inner class ViewHolder(var binding: DamagedCycleListRvDesignBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DamagedCycleAdapter.ViewHolder {
        var  binding = DamagedCycleListRvDesignBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return damagedCycleList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DamagedCycleAdapter.ViewHolder, position: Int) {

        holder.binding.cycleID.text = damagedCycleList.get(position).cycleID
        holder.binding.employeeId.text = damagedCycleList.get(position).empID

        holder.binding.repaired.setOnClickListener {

            Firebase.firestore.collection(CYCLE_FOLDER).document(damagedCycleList.get(position).cycleID!!).update("damaged","False")
            Firebase.firestore.collection(COMPLAINTS_FOLDER).document(damagedCycleList.get(position).cycleID!!).delete()
            removeAt(position)

        }




    }

    private fun removeAt(position: Int) {
        damagedCycleList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, damagedCycleList.size)
    }
}