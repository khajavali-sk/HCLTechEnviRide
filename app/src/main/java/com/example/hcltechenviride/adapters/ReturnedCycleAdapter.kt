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

    inner class ViewHolder(var binding: ReturnRvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReturnedCycleAdapter.ViewHolder {
        var binding = ReturnRvBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return returnedCycleList.size
    }

    override fun onBindViewHolder(holder: ReturnedCycleAdapter.ViewHolder, position: Int) {

        holder.binding.cycleID.text = returnedCycleList.get(position).cycleID
        holder.binding.duration.text = returnedCycleList.get(position).duration

        holder.binding.complain.setOnClickListener {
            Firebase.firestore.collection(COMPLAINTS_FOLDER)
                .document(returnedCycleList.get(position).cycleID!!)
                .set(returnedCycleList.get(position))
            Firebase.firestore.collection(CYCLE_FOLDER)
                .document(returnedCycleList.get(position).cycleID!!).update("damaged", "True")
            Firebase.firestore.collection(RETURNED_CYCLE_FOLDER)
                .document(returnedCycleList.get(position).cycleID!!).delete()
            removeAt(position)

        }

        holder.binding.accept.setOnClickListener {
            Firebase.firestore.collection(RETURNED_CYCLE_FOLDER)
                .document(returnedCycleList.get(position).cycleID!!).delete()
            removeAt(position)
        }


    }

    private fun removeAt(position: Int) {
        returnedCycleList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, returnedCycleList.size)
    }
}