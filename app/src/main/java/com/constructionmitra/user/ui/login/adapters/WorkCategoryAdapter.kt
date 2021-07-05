package com.constructionmitra.user.ui.login.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.constructionmitra.user.data.WorkCategory
import com.constructionmitra.user.databinding.ItemWorkOptionBinding

class WorkCategoryAdapter(
    private val list: List<WorkCategory>,
    private val onItemClick: (workCategory: WorkCategory) -> Unit
) :
    RecyclerView.Adapter<WorkCategoryAdapter.ViewHolder>() {

    private var selectedItem: Int = 0

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

        }
    }
}