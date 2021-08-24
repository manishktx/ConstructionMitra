package com.constructionmitra.user.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.constructionmitra.user.data.SelectWorkData
import com.constructionmitra.user.data.WorkHistory
import com.constructionmitra.user.databinding.ItemCatalogBigBinding
import com.constructionmitra.user.databinding.ItemJobRoleBinding
import com.constructionmitra.user.databinding.ItemSelectedWorkBinding

class SelectWorkAdapter(
    private val list: MutableList<SelectWorkData>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(ItemSelectedWorkBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bindData(list[position], position)
    }

    fun addWork(selectWorkData: SelectWorkData){
        list.add(selectWorkData)
        notifyDataSetChanged()
    }

    fun allWorkList() = list

    inner class ViewHolder(val binding: ItemSelectedWorkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(
            selectWorkData: SelectWorkData, position: Int
        ) {
            binding.jobTitle.text = position.plus(1).toString().plus(". ").plus(selectWorkData.work)
            binding.tvTeam.text = selectWorkData.numOfWorkRequired
        }
    }

}