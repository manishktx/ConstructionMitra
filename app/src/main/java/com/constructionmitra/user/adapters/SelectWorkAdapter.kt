package com.constructionmitra.user.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.constructionmitra.user.data.SelectWorkData
import com.constructionmitra.user.data.WorkHistory
import com.constructionmitra.user.databinding.ItemCatalogBigBinding
import com.constructionmitra.user.databinding.ItemJobRoleBinding
import com.constructionmitra.user.databinding.ItemSelectedWorkBinding
import com.google.gson.annotations.Until

class SelectWorkAdapter(
    private val list: MutableList<SelectWorkData>,
    private val enableDeleteOption: Boolean = false,
    private val deleteItem: (position: Int, selectWorkData: SelectWorkData) -> Unit
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
        notifyItemInserted(list.size - 1)
    }

    fun removeItem(jobWorkId: Int){
        if(list.any { it.jobWorkId == jobWorkId }){
            val position = list.indexOf(list.filter { it.jobWorkId == jobWorkId }[0])
            list.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun allWorkList() = list

    inner class ViewHolder(val binding: ItemSelectedWorkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(
            selectWorkData: SelectWorkData, position: Int
        ) {
            with(binding){
                enableDeleteOption.takeIf { true }?.let {
                    icDelete.visibility = View.VISIBLE
                    icDelete.setOnClickListener {
                        deleteItem(position, selectWorkData)
                    }
                } ?: run { icDelete.visibility = View.GONE }
            }
            binding.jobTitle.text = position.plus(1).toString().plus(". ").plus(selectWorkData.work)
            binding.tvTeam.text = selectWorkData.numOfWorkRequired
        }
    }

}