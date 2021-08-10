package com.constructionmitra.user.ui.profile

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.constructionmitra.user.R
import com.constructionmitra.user.data.WorkCategory
import com.constructionmitra.user.databinding.FragmentAboutBinding
import com.constructionmitra.user.databinding.FragmentAboutWorkExpBinding
import com.constructionmitra.user.databinding.ItemWorkExpBinding
import com.constructionmitra.user.databinding.ItemWorkOptionBinding
import com.constructionmitra.user.ui.login.adapters.CategoryAdapter
import kotlinx.android.synthetic.main.fragment_available_work_list.*

class WorkExpFragment : Fragment() {

    private lateinit var binding: FragmentAboutWorkExpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAboutWorkExpBinding.inflate(inflater, container, false).apply {
            rvWorkLocations.adapter = WorkExpAdapter(resources.getStringArray(R.array.work_exp_options).toList()){

            }
        }
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            WorkExpFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}

class WorkExpAdapter(
    val list: List<String>,
    private val onItemClick: (exp: String) -> Unit
): RecyclerView.Adapter<WorkExpAdapter.WorkExpViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkExpAdapter.WorkExpViewHolder {
        val binding:ItemWorkExpBinding = ItemWorkExpBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WorkExpViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: WorkExpAdapter.WorkExpViewHolder, position: Int) {
        holder.bindData(onItemClick, list[position])
    }

    inner class WorkExpViewHolder(val binding: ItemWorkExpBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(
            onItemClick: (exp: String) -> Unit,
            exp: String,
        ) {
            binding.exp = exp
            binding.root.setOnClickListener{
                onItemClick(exp)
            }
        }
    }
}