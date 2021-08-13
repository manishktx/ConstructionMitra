package com.constructionmitra.user.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.constructionmitra.user.data.*
import com.constructionmitra.user.databinding.ItemWorkLocationBinding

class LocationAdapter(
    private val list: List<Location>,
    private val onItemClick: (location: Location) -> Unit
) : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding: ItemWorkLocationBinding = ItemWorkLocationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LocationViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bindData(onItemClick, list[position])
    }


    inner class LocationViewHolder(val binding: ItemWorkLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(
            onItemClick: (location: Location) -> Unit,
            location: Location,
        ) {
            binding.tvPlaceName.text = location.city
            binding.ivTick.visibility = if(location.isChecked) View.VISIBLE else View.GONE
            binding.root.setOnClickListener{
                location.isChecked = !location.isChecked
                this@LocationAdapter.notifyDataSetChanged()
            }
        }
    }
}