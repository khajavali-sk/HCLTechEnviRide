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
        holder.binding.colorAndLocation.text = cycleItem.color+" - "+cycleItem.location

        holder.binding.delete.setOnClickListener {


            if (cycleList[position].allotted == "True") {

                Toast.makeText(context, "Can't Delete Cycle is in use", Toast.LENGTH_SHORT).show()

            } else {

                val builder = android.app.AlertDialog.Builder(context)

                builder.setTitle("Confirm Delete!")
                builder.setMessage("Do you want to delete this Cycle")

                builder.setNegativeButton("Confirm") { dialog, which ->
                    Firebase.firestore.collection(CYCLE_FOLDER)
                        .document(cycleList[position].cycleID!!).delete()
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
    }

    private fun removeAt(position: Int) {
        cycleList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, cycleList.size)
    }
}