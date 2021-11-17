package com.constructionmitra.user.ui.login.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.constructionmitra.user.data.JobCategory
import com.constructionmitra.user.data.WorkCategory
import com.constructionmitra.user.databinding.ItemWorkOptionBinding


class CategoryAdapter(
    private val list: List<JobCategory>,
    private val isSubCategory: Boolean = false,
    private val onItemClick: (jobCategory: JobCategory) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
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

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        holder.bindData(onItemClick, list[position])
    }

    inner class ViewHolder(val binding: ItemWorkOptionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(
            onItemClick: (jobCategory: JobCategory) -> Unit,
            jobCategory: JobCategory,
        ) {
            binding.data = jobCategory
            binding.tvCategory.text = jobCategory.category
            jobCategory.workDesc?.takeIf { it.isNotEmpty() }?.let {
                binding.tvDesc.text = it
            }
            binding.root.setOnClickListener{
                onItemClick(jobCategory)
            }
        }
    }
}