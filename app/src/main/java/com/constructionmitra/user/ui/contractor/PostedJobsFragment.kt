package com.constructionmitra.user.ui.contractor

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.constructionmitra.user.FragmentContainerActivity
import com.constructionmitra.user.R
import com.constructionmitra.user.adapters.PostedJobsAdapter
import com.constructionmitra.user.data.AppPreferences
import com.constructionmitra.user.data.PostedJob
import com.constructionmitra.user.databinding.FragmentPostedJobsBinding
import com.constructionmitra.user.databinding.ProgressBarBinding
import com.constructionmitra.user.ui.contractor.viewmodels.ContractorProfileViewModel
import com.constructionmitra.user.ui.contractor.viewmodels.UiViewModel
import com.constructionmitra.user.ui.dialogs.AppliedByDialog
import com.constructionmitra.user.ui.employer.ViewJobDetailsFragment
import com.constructionmitra.user.utilities.UpdateJobPost
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
            binding.rvPostedJobs.adapter = PostedJobsAdapter(
                it.postedJobDataList,
                onAppliedJobsClick = { postedJob ->
                    AppliedByDialog.newInstance(
                        postedJob.jobRole,
                        postedJob.applicantData, {}, {}
                    ).show(parentFragmentManager, "applied_by_applicants")
                },
                onMenuSelected = { view, postedJob ->
                    showMenu(view, postedJob)
                },
                onItemClick = { postedJob ->
                    navigateToJobDetails(postedJob)
                }
            )
        }
        profileViewModel.jobDeleted.observe(viewLifecycleOwner){
            showProgress(false)
            it?.let { _postedJob ->
                (binding.rvPostedJobs.adapter as PostedJobsAdapter).delete(_postedJob)
            }
        }
        profileViewModel.jobPublished.observe(viewLifecycleOwner){
            it?.let {
                profileViewModel.getProfileWithPostedJobs(
                    appPreferences.getUserId()
                )
            }
        }
        onError()
    }

    private fun navigateToJobDetails(postedJob: PostedJob) {
        Intent(requireActivity(), FragmentContainerActivity::class.java).apply {
            putExtra(FragmentContainerActivity.FRAGMENT_NAME, ViewJobDetailsFragment::class.java.name)
            putExtra(FragmentContainerActivity.PARCELABLE_POSTED_JOB, postedJob)
            startActivity(this)
        }
        requireActivity().overridePendingTransition(
            R.anim.enter_anim_activity,
            R.anim.exit_anim_activity
        )
    }

    private fun showMenu(view: View, postedJob: PostedJob) {
        val popup = PopupMenu(
            requireContext(), view, Gravity.END, 0,
            R.style.App_PopupMenu
        )
        popup.menuInflater.inflate(R.menu.posted_job_menu, popup.menu)
        if(postedJob.isPublished == 1)
            popup.menu.findItem(R.id.optionPublish).setVisible(false)

        popup.apply {
            setOnMenuItemClickListener { menu ->
                when(menu.itemId){
                    R.id.optionDelete -> {
                        // delete item
                        showProgress(true)
                        profileViewModel.updateJobStatus(
                            postedJob, appPreferences.getUserId(), UpdateJobPost.DELETE.param
                        )
                    }
                    R.id.optionPublish -> {
                        // delete item
                        showProgress(true)
                        profileViewModel.updateJobStatus(
                            postedJob, appPreferences.getUserId(), UpdateJobPost.PUBLISHED.param, true
                        )
                    }
                }
                true
            }
        }.show()
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