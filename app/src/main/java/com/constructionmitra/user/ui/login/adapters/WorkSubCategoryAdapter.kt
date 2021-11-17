package com.constructionmitra.user.ui.login.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.constructionmitra.user.data.JobRole
import com.constructionmitra.user.databinding.ItemWorkSubCategoryBinding
import com.constructionmitra.user.utilities.showToast

class WorkSubCategoryAdapter(
    private val list: List<JobRole>,
    private val isSubCategory: Boolean = false,
    private val onItemClick: (jobRole: List<JobRole>) -> Unit
) :
    RecyclerView.Adapter<WorkSubCategoryAdapter.ViewHolder>() {

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
        holder.bindData(onItemClick, list[position], position)
    }


    inner class ViewHolder(val binding: ItemWorkSubCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bindData(
            onItemClick: (jobRole: List<JobRole>) -> Unit,
            jobRole: JobRole, position: Int
        ) {
            binding.jobRole = jobRole
            binding.tvCategory.text = jobRole.jobRoleHn.plus("\n").plus(jobRole.jobRole)
            binding.ivTick.visibility = if(jobRole.isChecked) View.VISIBLE else View.GONE
            jobRole.color?.let {
                binding.cardView.setCardBackgroundColor(Color.parseColor(it))
            }

            binding.root.setOnClickListener{
                if(list.filter { it.isChecked }.size >= 3 && !jobRole.isChecked){
                    binding.root.showToast("Maximum 3 works can be selected at a time. ")
                    return@setOnClickListener
                }
                jobRole.isChecked = !jobRole.isChecked
                this@WorkSubCategoryAdapter.notifyItemChanged(position)
            }
        }
    }
}