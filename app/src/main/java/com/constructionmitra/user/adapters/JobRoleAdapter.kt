package com.constructionmitra.user.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.constructionmitra.user.data.Job
import com.constructionmitra.user.data.JobRole
import com.constructionmitra.user.data.Work
import com.constructionmitra.user.data.WorkCategory
import com.constructionmitra.user.databinding.ItemJobRoleBinding
import com.constructionmitra.user.databinding.ItemWorkBinding

class JobRoleAdapter(
    private val list: List<JobRole>,
    private val noOfWorker: String,
    private val onItemClick: (jobRole: JobRole) -> Unit
) : RecyclerView.Adapter<JobRoleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemJobRoleBinding = ItemJobRoleBinding.inflate(
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


    inner class ViewHolder(val binding: ItemJobRoleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(
            onItemClick: (jobRole: JobRole) -> Unit,
            jobRole: JobRole,
        ) {
            binding.jobRole = jobRole
            binding.numberOfWorker = noOfWorker
            binding.executePendingBindings()
        }
    }
}