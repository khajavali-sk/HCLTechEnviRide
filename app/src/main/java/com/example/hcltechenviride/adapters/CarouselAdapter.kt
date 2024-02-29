package com.example.hcltechenviride.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hcltechenviride.databinding.CarouselRvDesignBinding
//import kotlinx.android.synthetic.main.item_carousel.view.*

class CarouselAdapter(var context: Context,private val images: List<Int>) : RecyclerView.Adapter<CarouselAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: CarouselRvDesignBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = CarouselRvDesignBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageRes = images[position % images.size]
        holder.binding.imageView.setImageResource(imageRes)
    }

    override fun getItemCount(): Int = Int.MAX_VALUE


}

