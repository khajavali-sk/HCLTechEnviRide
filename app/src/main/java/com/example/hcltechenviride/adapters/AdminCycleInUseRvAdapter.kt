package com.example.hcltechenviride.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.hcltechenviride.Models.CurrentCycle
import com.example.hcltechenviride.databinding.AdminCycleInUseRvDesignBinding

class AdminCycleInUseRvAdapter(var context: Context, var cycleInUseList: ArrayList<CurrentCycle>) :
    RecyclerView.Adapter<AdminCycleInUseRvAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: AdminCycleInUseRvDesignBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = AdminCycleInUseRvDesignBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cycleInUseList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currCycle = cycleInUseList[position]
        holder.binding.cycleID.text = currCycle.cycleID
        holder.binding.employeeId.text = currCycle.empID
        holder.binding.allottedTime.text = currCycle.allottedTime
    }
}