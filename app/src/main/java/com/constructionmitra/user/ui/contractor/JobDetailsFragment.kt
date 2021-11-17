package com.constructionmitra.user.ui.contractor

import android.content.Intent
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.constructionmitra.user.R
import com.constructionmitra.user.data.AppPreferences
import com.constructionmitra.user.databinding.*
import com.constructionmitra.user.ui.contractor.viewmodels.JobPostViewModel
import com.constructionmitra.user.utilities.constants.AppConstants
import com.constructionmitra.user.utilities.constants.Role
import com.constructionmitra.user.utilities.ext.setDestination
import com.constructionmitra.user.utilities.showToast
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class JobDetailsFragment : Fragment() {

    private var _binding: FragmentJobDetailsBinding? = null
    private lateinit var progressBarBinding: ProgressBarBinding

    private val viewModel: JobPostViewModel by lazy {
        ViewModelProvider(requireActivity()).get(JobPostViewModel::class.java)
    }

    private val args: JobDetailsFragmentArgs by navArgs()
    @Inject
    lateinit var appPreferences: AppPreferences
    // to manage the indicator state
    private val mIndicatorStates = hashMapOf<Int, Int>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJobDetailsBinding.inflate(inflater, container, false).apply {
            progressBarBinding = ProgressBarBinding.bind(root)
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when(args.role){
            Role.PETTY_CONTRACTOR -> {
                setDestination(
                    R.id.nav_host_fragment_content_winner,
                    R.navigation.job_role_details_navigation, R.id.jobRoleDetails
                )
            }
            Role.ENGINEER_SUPERVISOR -> {
                setDestination(
                    R.id.nav_host_fragment_content_winner,
                    R.navigation.job_role_details_navigation, R.id.jobRoleDetailsESFragment
                )
            }
            Role.SPECIALISED_AGENCY -> {
                setDestination(
                    R.id.nav_host_fragment_content_winner,
                    R.navigation.job_role_details_navigation, R.id.jobRoleDetailsSAFragment
                )
            }
        }

        with(binding){
            tvContinue1.setOnClickListener {
                viewModel.navigateToAddEmployeeDetails()
            }
            tvBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
            tvContinue.setOnClickListener {
                viewModel.navigateToReviewYourJob()
            }
            tvSave.setOnClickListener {
                // Save post
                showProgress(true)
                viewModel.postJob(appPreferences.getUserId())
            }
            tvPostAJob.setOnClickListener {
                // Save post
                showProgress(true)
                viewModel.postJob(appPreferences.getUserId())
            }
        }
        registerObservers()
    }

    private fun registerObservers() {
        viewModel.currentFragment.observe(viewLifecycleOwner){
            binding.position = it
            Timber.d("Which Fragment observed! position = $it")
            // set story indicator
            it?.let { _position ->
                setIndicator(_position)
            }
        }

        viewModel.jobPosted.observe(viewLifecycleOwner){
            showProgress(false)
            if(it){
                binding.root.showToast("Job Posted")
                navigateToContractorProfile()
            } else {
                binding.root.showToast("Something went wrong")
            }
        }
    }

    private fun setIndicator(position: Int){
        with(binding.indicatorView.root){
            val childCount = childCount - 1
            for (i in 0..childCount){
                val transitionDrawable = getChildAt(i).background as TransitionDrawable

                if(position >= i) {
                    if (i == position && mIndicatorStates[i] != 1){
                        mIndicatorStates[i] = 1
                        transitionDrawable.startTransition(DEFAULT_INDICATOR_TRANSITION)
                    }
                }
                else{
                    mIndicatorStates[i] = 0
                    transitionDrawable.resetTransition()
                }
            }
        }
    }

    private fun navigateToContractorProfile(){
        appPreferences.saveBoolean(AppPreferences.IS_NEW_CONTRACTOR, false)
        appPreferences.saveUserType(AppConstants.USER_TYPE_CONTRACTOR)
        Intent(context, EmployerMainActivity::class.java).apply {
            requireContext().startActivity(this)
            requireActivity().finish()
        }
        requireActivity().overridePendingTransition(R.anim.enter_anim_activity, R.anim.exit_anim_activity)
    }

    private fun showProgress(show: Boolean) {
        progressBarBinding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        private const val DEFAULT_INDICATOR_TRANSITION = 200
    }
}