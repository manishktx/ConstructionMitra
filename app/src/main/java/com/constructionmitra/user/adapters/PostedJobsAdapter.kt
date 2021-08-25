package com.constructionmitra.user.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.constructionmitra.user.data.PostedJob
import com.constructionmitra.user.databinding.ItemPostedJobBinding

class PostedJobsAdapter(
    private val list: List<PostedJob>,
    private val onItemClick: (postedJob: PostedJob) -> Unit
) : RecyclerView.Adapter<PostedJobsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemPostedJobBinding = ItemPostedJobBinding.inflate(
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


    inner class ViewHolder(val binding: ItemPostedJobBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(
            onItemClick: (postedJob: PostedJob) -> Unit,
            postedJob: PostedJob,
        ) {
            binding.data = postedJob
            binding.executePendingBindings()
        }
    }
}