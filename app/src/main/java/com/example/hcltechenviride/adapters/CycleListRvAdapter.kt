package com.example.hcltechenviride.adapters


import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.hcltechenviride.Models.Cycle
import com.example.hcltechenviride.databinding.CycleListRvDesignBinding

class CycleListRvAdapter(var context: Context, var cycleList: ArrayList<Cycle>) :
    RecyclerView.Adapter<CycleListRvAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: CycleListRvDesignBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = CycleListRvDesignBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cycleList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.root
        val cycleItem = cycleList[position]
        holder.binding.cycleID.text = cycleItem.cycleID
        holder.binding.color.text = cycleItem.color
        holder.binding.location.text = cycleItem.location
    }
}