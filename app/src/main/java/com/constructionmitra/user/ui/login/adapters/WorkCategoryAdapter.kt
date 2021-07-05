package com.constructionmitra.user.ui.login.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.constructionmitra.user.data.WorkCategory
import com.constructionmitra.user.databinding.ItemWorkOptionBinding

class WorkCategoryAdapter(
    private val list: List<WorkCategory>,
    private val isSubCategory: Boolean = false,
    private val onItemClick: (workCategory: WorkCategory) -> Unit
) :
    RecyclerView.Adapter<WorkCategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemWorkOptionBinding = ItemWorkOptionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(onItemClick, list[position])
    }


    inner class ViewHolder(val binding: ItemWorkOptionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(
            onItemClick: (workCategory: WorkCategory) -> Unit,
            workCategory: WorkCategory,
        ) {
            binding.tvCategory.text = workCategory.name
            binding.tvDesc.text = workCategory.desc
            if(isSubCategory) {
                binding.cardView.setCardBackgroundColor(Color.parseColor(workCategory.backgroundColor))
                binding.tvDesc.visibility = View.GONE
            }

            binding.root.setOnClickListener{
                onItemClick(workCategory)
            }
        }
    }
}