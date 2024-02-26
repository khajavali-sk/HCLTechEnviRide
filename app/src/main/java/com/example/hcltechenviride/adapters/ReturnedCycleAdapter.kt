package com.example.hcltechenviride.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.hcltechenviride.Models.History
import com.example.hcltechenviride.databinding.ReturnRvBinding

class ReturnedCycleAdapter(var context: Context, var returnedCycleList: ArrayList<History>) :
    RecyclerView.Adapter<ReturnedCycleAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: ReturnRvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReturnedCycleAdapter.ViewHolder {
        var  binding = ReturnRvBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return returnedCycleList.size
    }

    override fun onBindViewHolder(holder: ReturnedCycleAdapter.ViewHolder, position: Int) {

        holder.binding.cycleID.text = returnedCycleList.get(position).cycleID
        holder.binding.duration.text = returnedCycleList.get(position).duration
    }
}