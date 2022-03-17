package com.constructionmitra.user.ui.contractor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.constructionmitra.user.data.AppPreferences
import com.constructionmitra.user.databinding.FragmentEngineerJobRoleDetailsBinding
import com.constructionmitra.user.databinding.ProgressBarBinding
import com.constructionmitra.user.ui.contractor.viewmodels.JobPostViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EngineerJobDetailsFragment : Fragment() {

    private var _binding: FragmentEngineerJobRoleDetailsBinding? = null
    private lateinit var progressBarBinding: ProgressBarBinding

    private val viewModel: JobPostViewModel by lazy {
        ViewModelProvider(requireActivity()).get(JobPostViewModel::class.java)
    }
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
        _binding = FragmentEngineerJobRoleDetailsBinding.inflate(inflater, container, false).apply {
            progressBarBinding = ProgressBarBinding.bind(root)
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
//            tvContinue1.setOnClickListener {
//                viewModel.navigateToAddEmployeeDetails()
//            }
//            tvBack.setOnClickListener {
//                requireActivity().onBackPressed()
//            }
//            tvContinue.setOnClickListener {
//                viewModel.navigateToReviewYourJob()
//            }
//            tvSave.setOnClickListener {
//                // Save post
//                showProgress(true)
//                viewModel.postJob(appPreferences.getUserId())
//            }
//            tvPostAJob.setOnClickListener {
//                // Save post
//                showProgress(true)
//                viewModel.postJob(appPreferences.getUserId())
//            }
//        }
//        registerObservers()
    }

//    private fun registerObservers() {
//        viewModel.currentFragment.observe(viewLifecycleOwner){
//            binding.position = it
//            Timber.d("Which Fragment observed! position = $it")
//            // set story indicator
//            it?.let { _position ->
//                setIndicator(_position)
//            }
//        }
//
//        viewModel.jobPosted.observe(viewLifecycleOwner){
//            showProgress(false)
//            if(it){
//                binding.root.showToast("Job Posted")
//                navigateToContractorProfile()
//            } else {
//                binding.root.showToast("Something went wrong")
//            }
//        }
//    }
//
//    private fun setIndicator(position: Int){
//        with(binding.indicatorView.root){
//            val childCount = childCount - 1
//            for (i in 0..childCount){
//                val transitionDrawable = getChildAt(i).background as TransitionDrawable
//
//                if(position >= i) {
//                    if (i == position && mIndicatorStates[i] != 1){
//                        mIndicatorStates[i] = 1
//                        transitionDrawable.startTransition(DEFAULT_INDICATOR_TRANSITION)
//                    }
//                }
//                else{
//                    mIndicatorStates[i] = 0
//                    transitionDrawable.resetTransition()
//                }
//            }
//        }
//    }
//
//    private fun navigateToContractorProfile(){
//        appPreferences.saveBoolean(AppPreferences.IS_NEW_CONTRACTOR, false)
//        appPreferences.saveUserType(AppConstants.USER_TYPE_CONTRACTOR)
//        Intent(context, ContractorMainActivity::class.java).apply {
//            requireContext().startActivity(this)
//            requireActivity().finish()
//        }
//        requireActivity().overridePendingTransition(R.anim.enter_anim_activity, R.anim.exit_anim_activity)
//    }
//
//    private fun showProgress(show: Boolean) {
//        progressBarBinding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//
//    companion object{
//        private const val DEFAULT_INDICATOR_TRANSITION = 200
//    }
}}