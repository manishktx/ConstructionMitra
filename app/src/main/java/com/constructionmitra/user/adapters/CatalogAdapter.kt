package com.constructionmitra.user.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.constructionmitra.user.data.WorkHistory
import com.constructionmitra.user.databinding.ItemCatalogBigBinding

class CatalogAdapter(
    private val list: List<WorkHistory>,
    private val onItemClick: (workHistory: WorkHistory) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(ItemCatalogBigBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bindData(onItemClick = onItemClick, list[position])
    }


    inner class ViewHolder(val binding: ItemCatalogBigBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(
            onItemClick: (workHistory: WorkHistory) -> Unit,
            workHistory: WorkHistory,
        ) {
            binding.imageUrl = workHistory.image
            binding.root.setOnClickListener {
                onItemClick(workHistory)
            }
        }
    }

}