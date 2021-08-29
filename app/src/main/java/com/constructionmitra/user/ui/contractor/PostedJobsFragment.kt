package com.constructionmitra.user.ui.contractor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.constructionmitra.user.adapters.PostedJobsAdapter
import com.constructionmitra.user.data.AppPreferences
import com.constructionmitra.user.databinding.FragmentPostedJobsBinding
import com.constructionmitra.user.databinding.ProgressBarBinding
import com.constructionmitra.user.ui.contractor.viewmodels.ContractorProfileViewModel
import com.constructionmitra.user.ui.contractor.viewmodels.UiViewModel
import com.constructionmitra.user.utilities.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PostedJobsFragment : Fragment() {

    private var _binding: FragmentPostedJobsBinding? = null
    private lateinit var progressBarBinding: ProgressBarBinding

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val uiViewModel: UiViewModel by lazy {
        ViewModelProvider(requireActivity()).get(UiViewModel::class.java)
    }
    @Inject
    lateinit var appPreferences: AppPreferences
    private val profileViewModel: ContractorProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostedJobsBinding.inflate(inflater, container, false).apply {
            progressBarBinding = ProgressBarBinding.bind(root)
            tvPostAJob.setOnClickListener {
                uiViewModel.initJobPostProcess(true)
            }
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgress(true)
        profileViewModel.getProfileWithPostedJobs(
            appPreferences.getUserId()
        )
        registerObservers()
    }

    private fun registerObservers(){
        profileViewModel.profileDataWithPostedJob.observe(viewLifecycleOwner){
            showProgress(false)
            // setAdapter
            binding.headerProfile.tvPostedJobsCount.text = it.totalJobs.toString()
            binding.headerProfile.tvAppliedJobsCount.text = it.totalAppliedUser.toString()
            binding.rvPostedJobs.adapter = PostedJobsAdapter(it.postedJobDataList){

            }
        }
        onError()
    }

    private fun onError() {
        profileViewModel.errorMsg.observe(viewLifecycleOwner){
            showProgress(false)
            binding.root.showToast(it!!)
        }
    }
    private fun showProgress(show: Boolean) {
        progressBarBinding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}