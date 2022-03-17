package com.constructionmitra.user.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.constructionmitra.user.R
import com.constructionmitra.user.data.Job
import com.constructionmitra.user.data.Work
import com.constructionmitra.user.data.WorkCategory
import com.constructionmitra.user.databinding.ItemWorkBinding
import com.constructionmitra.user.utilities.TimeUtils

class HomeAdapter(
    private val list: List<Job>,
    private val isAvailableJob: Boolean = true,
    private val onItemClick: (job: Job) -> Unit,
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemWorkBinding = ItemWorkBinding.inflate(
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


    inner class ViewHolder(val binding: ItemWorkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(
            onItemClick: (job: Job) -> Unit,
            job: Job,
        ) {
            binding.data = job
            binding.isAvailableJob = isAvailableJob
            binding.executePendingBindings()

            // set post created date
            job.dateTime?.let {
                date ->
                val postedDay = TimeUtils.getDayOnlyFromTimeStamp(date)
                val currentDay = TimeUtils.getDayOnlyFromCurrentDate()
                binding.tvJobPostedOn.text = binding.root.context.getString(
                    if(postedDay == currentDay)  R.string.job_created_today else
                        R.string.job_created_on_formatter, currentDay.minus(postedDay)
                )
            }
            binding.tvRequestForWork.setOnClickListener {
                if(isAvailableJob)
                    onItemClick(job)
            }
            binding.cardView.setOnClickListener {
                if(isAvailableJob)
                    onItemClick(job)
            }
        }
    }
}