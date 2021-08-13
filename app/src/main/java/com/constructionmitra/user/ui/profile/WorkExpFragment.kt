package com.constructionmitra.user.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.constructionmitra.user.api.ProfileRequests
import com.constructionmitra.user.data.AppPreferences
import com.constructionmitra.user.data.WorkExperience
import com.constructionmitra.user.databinding.FragmentAboutWorkExpBinding
import com.constructionmitra.user.databinding.ItemWorkExpBinding
import com.constructionmitra.user.databinding.ProgressBarBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WorkExpFragment : Fragment() {

    private var adapter: WorkExpAdapter? = null
    private lateinit var binding: FragmentAboutWorkExpBinding
    private lateinit var progressBarBinding: ProgressBarBinding

    private val viewModel: ProfileViewModel by viewModels()

    @Inject lateinit var appPreferences: AppPreferences
    @Inject lateinit var profileRequests: ProfileRequests

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
            progressBarBinding = ProgressBarBinding.bind(root)
            tvSave.setOnClickListener {
                adapter?.let {
                    if(it.currentSelection >= 0){
                        // Update work exp
                        showProgress(true)
                        viewModel.updateProfile(profileRequests.updateExp(
                            appPreferences.getUserId()!!,
                            appPreferences.getToken()!!,
                            viewModel.workExpOptions.value?.get(it.currentSelection)!!
                        ))
                    }
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgress(true)
        viewModel.getWorkExpOptions()
        viewModel.workExpOptions.observe(viewLifecycleOwner){
            showProgress(false)
            binding.rvWorkExp.adapter = WorkExpAdapter(it){

            }.apply {
                adapter = this
            }
        }
    }

    private fun showProgress(show: Boolean) {
        progressBarBinding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
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
    val list: List<WorkExperience>,
    private val onItemClick: (exp: WorkExperience) -> Unit
): RecyclerView.Adapter<WorkExpAdapter.WorkExpViewHolder>() {
    private var _currentSelection = -1
    val currentSelection
    get() = _currentSelection

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
            onItemClick: (exp: WorkExperience) -> Unit,
            exp: WorkExperience,
        ) {
            binding.exp = exp
            binding.ivTick.visibility =
                if( adapterPosition == _currentSelection) View.VISIBLE else View.GONE

            binding.root.setOnClickListener{
                _currentSelection = adapterPosition
                notifyDataSetChanged()
            }
        }
    }
}