package com.constructionmitra.user.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.constructionmitra.user.data.Work
import com.constructionmitra.user.data.WorkCategory
import com.constructionmitra.user.databinding.ItemWorkBinding

class HomeAdapter(
    private val list: List<Work>,
    private val onItemClick: (work: Work) -> Unit
) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

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
            onItemClick: (work: Work) -> Unit,
            work: Work,
        ) {
            binding.data = work
            binding.executePendingBindings()
        }
    }
}