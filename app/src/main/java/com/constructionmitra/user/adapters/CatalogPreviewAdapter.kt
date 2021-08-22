package com.constructionmitra.user.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.constructionmitra.user.R
import com.constructionmitra.user.data.WorkHistory
import com.constructionmitra.user.databinding.ItemCatalogBinding
import com.constructionmitra.user.databinding.ItemViewAllCatalogBinding

class CatalogPreviewAdapter(
    private val list: List<WorkHistory>,
    private val onItemClick: (workHistory: WorkHistory) -> Unit,
    private val onViewAllClick: (workHistoryList: List<WorkHistory>) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            ITEM_WORK -> {
               ViewHolder( ItemCatalogBinding.inflate(
                   LayoutInflater.from(parent.context),
                   parent,
                   false
               ))
            }
            else -> {
                FooterViewHolder(
                    ItemViewAllCatalogBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                    ))
            }
        }
    }

    override fun getItemCount(): Int {
        return if(list.size >= 5) 5 else list.size
    }

    override fun getItemViewType(position: Int): Int {
        return if(list.size >= 5 && position == 4)
            ITEM_TYPE_VIEW_ALL
        else
            ITEM_WORK
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(position < 4){
            (holder as ViewHolder).bindData(onItemClick = onItemClick, list[position])
        }
        else{
            (holder as FooterViewHolder).bindData(onViewAllClick = onViewAllClick)
        }
    }


    inner class ViewHolder(val binding: ItemCatalogBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(
            onItemClick: (workHistory: WorkHistory) -> Unit,
            workHistory: WorkHistory,
        ) {
            binding.workHistory = workHistory
            binding.ivWork.setOnClickListener {
                onItemClick(workHistory)
            }
        }
    }

    inner class FooterViewHolder(val binding: ItemViewAllCatalogBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(
            onViewAllClick: (workHistoryList: List<WorkHistory>) -> Unit,
        ) {
            binding.tvWork.text = binding.root.context.getString(R.string.view_all_formatter, list.size.toString())
            binding.root.setOnClickListener {
                onViewAllClick(list)
            }
        }
    }

    companion object{
        const val ITEM_TYPE_VIEW_ALL: Int = 2
        const val ITEM_WORK: Int = 1
    }

}