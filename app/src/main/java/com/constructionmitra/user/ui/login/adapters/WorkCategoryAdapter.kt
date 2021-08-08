package com.constructionmitra.user.ui.login.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.constructionmitra.user.data.JobRole
import com.constructionmitra.user.data.WorkCategory
import com.constructionmitra.user.databinding.ItemWorkOptionBinding
import com.constructionmitra.user.databinding.ItemWorkSubCategoryBinding

class WorkCategoryAdapter(
    private val list: List<JobRole>,
    private val isSubCategory: Boolean = false,
    private val onItemClick: (jobRole: JobRole) -> Unit
) :
    RecyclerView.Adapter<WorkCategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemWorkSubCategoryBinding = ItemWorkSubCategoryBinding.inflate(
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


    inner class ViewHolder(val binding: ItemWorkSubCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(
            onItemClick: (jobRole: JobRole) -> Unit,
            jobRole: JobRole,
        ) {
            binding.tvCategory.text = jobRole.jobRole
            if(isSubCategory) {
                binding.cardView.setCardBackgroundColor(Color.parseColor("#FFBDC7"))
            }

            binding.root.setOnClickListener{
                onItemClick(jobRole)
            }
        }
    }
}