package com.constructionmitra.user.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.constructionmitra.user.R
import com.constructionmitra.user.data.PostedJob
import com.constructionmitra.user.databinding.ItemJobPostCardEsBinding
import com.constructionmitra.user.databinding.ItemJobPostCardPcBinding
import com.constructionmitra.user.utilities.constants.Role

class PostedJobsAdapter(
    private val list: List<PostedJob>,
    private val onItemClick: (postedJob: PostedJob) -> Unit,
    private val onAppliedJobsClick: (postedJob: PostedJob) -> Unit
) : RecyclerView.Adapter<BaseViewHolder<PostedJob>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<PostedJob> {
        return when(viewType){
            VIEW_TYPE_ES -> ESViewHolder(
                ItemJobPostCardEsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
            ))
            else -> {
                ViewHolder(
                    ItemJobPostCardPcBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ))
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return when(true){
            list[position].categoryName.contains(Role.ENGINEER_SUPERVISOR.name, ignoreCase = true) -> {
                VIEW_TYPE_ES
            }
            list[position].categoryName.contains(Role.SPECIALISED_AGENCY.name, ignoreCase = true) -> {
                VIEW_TYPE_SA
            }
            else -> {
                VIEW_TYPE_PC
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<PostedJob>, position: Int) {
        holder.bind(list[position], onItemClick,)
    }


    inner class ViewHolder(val binding: ItemJobPostCardPcBinding) :
        BaseViewHolder<PostedJob>(binding.root) {
        override fun bind(job: PostedJob, onIteClick: (t: PostedJob) -> Unit) {
            binding.data = job
            binding.tvRequestForWork.text = binding.root.context.getString(R.string.applied_by_formatter, job.totalAppliedByUser)
            binding.executePendingBindings()
            binding.tvRequestForWork.setOnClickListener {
                if(job.applicantData.isNotEmpty()){
                    onAppliedJobsClick(job)
                }
            }
        }
    }

    inner class ESViewHolder(val binding: ItemJobPostCardEsBinding) :
        BaseViewHolder<PostedJob>(binding.root) {

        override fun bind(job: PostedJob, onIteClick: (t: PostedJob) -> Unit) {
            binding.data = job
            binding.tvRequestForWork.text = binding.root.context.getString(R.string.applied_by_formatter, job.totalAppliedByUser)
            binding.executePendingBindings()
            binding.tvRequestForWork.setOnClickListener {
                if(job.applicantData.isNotEmpty()){
                    onAppliedJobsClick(job)
                }
            }
        }
    }

    companion object {
        const val VIEW_TYPE_ES = 0X1
        const val VIEW_TYPE_PC = 0X2
        const val VIEW_TYPE_SA = 0X3
    }
}