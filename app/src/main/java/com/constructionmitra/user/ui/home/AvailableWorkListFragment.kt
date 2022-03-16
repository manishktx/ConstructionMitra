package com.constructionmitra.user.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.constructionmitra.user.FragmentContainerActivity
import com.constructionmitra.user.R
import com.constructionmitra.user.adapters.HomeAdapter
import com.constructionmitra.user.adapters.MY_PAGE_INDEX
import com.constructionmitra.user.adapters.PLANT_LIST_PAGE_INDEX
import com.constructionmitra.user.data.AppPreferences
import com.constructionmitra.user.data.Job
import com.constructionmitra.user.data.dummyWorkList
import com.constructionmitra.user.databinding.FragmentAvailableWorkListBinding
import com.constructionmitra.user.databinding.FragmentHomeBinding
import com.constructionmitra.user.databinding.ProgressBarBinding
import com.constructionmitra.user.ui.work.WorkDetailsFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 *  Class used to show the list of available works and applied works
 *  manages data by the check of {showAppliedWork}
 */
@AndroidEntryPoint
class AvailableWorkListFragment : Fragment() {

    private var showAppliedWork: Boolean = false
    private var _binding: FragmentAvailableWorkListBinding? = null
    private lateinit var progressBarBinding: ProgressBarBinding

    @Inject
    lateinit var appPreferences: AppPreferences

    private val binding get() = _binding!!

    private val viewModel: AvailableJobsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            showAppliedWork = it.getBoolean(SHOW_APPLIED_WORK)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAvailableWorkListBinding.inflate(inflater, container, false).apply {
            progressBarBinding = ProgressBarBinding.bind(root)
            isShowingAvailableJobs = !showAppliedWork
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgress(true)

        if(showAppliedWork){
            // Show list of applied work
            viewModel.getAvailableJobs(
                appPreferences.getUserId()!!,
                limit = DEFAULT_LIMIT,
                offset = DEFAULT_OFFSET,
                locationId = "all", false
            )
        }
        else {
            // Show list of available work
            viewModel.getAvailableJobs(
                appPreferences.getUserId()!!,
                limit = DEFAULT_LIMIT,
                offset = DEFAULT_OFFSET,
                locationId = "all", true
            )
        }

        viewModel.availableJobs.observe(viewLifecycleOwner){
            showProgress(false)
            it?.takeIf {
                !it.isNullOrEmpty()
            }?.let {
                _list ->
                // set adapter
                setAdapter(_list)

            }?: run {
                // No jobs available
                binding.tvWorkNotAvailable.visibility = View.VISIBLE
            }
        }

        viewModel.appliedJobs.observe(viewLifecycleOwner){
            showProgress(false)
            it?.takeIf {
                !it.isNullOrEmpty()
            }?.let {
                    _list ->
                // set adapter
                setAdapter(_list)

            }?: run {
                // No jobs available
                binding.tvWorkNotAvailable.visibility = View.VISIBLE
            }
        }
    }

    private fun setAdapter(jobs: List<Job>){
        binding.rvWork.adapter = HomeAdapter(
            jobs,
            isAvailableJob =  !showAppliedWork
        ){
            Intent(requireContext(), FragmentContainerActivity::class.java).apply {
                putExtra(FragmentContainerActivity.FRAGMENT_NAME, WorkDetailsFragment::class.java.name)
                putExtra(FragmentContainerActivity.PARCELABLE_KEY, it)
                startActivity(this)
            }
            requireActivity().overridePendingTransition(
                R.anim.enter_anim_activity,
                R.anim.exit_anim_activity
            )
        }
    }

    private fun showProgress(show: Boolean) {
        progressBarBinding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        private const val DEFAULT_LIMIT = 200;
        private const val DEFAULT_OFFSET = 0;
        private const val SHOW_APPLIED_WORK  = "key_show_applied_work"

        fun withAppliedWork() = AvailableWorkListFragment().apply {
                arguments = Bundle().apply {
                    this.putBoolean(SHOW_APPLIED_WORK, true)
                }
            }
    }
}