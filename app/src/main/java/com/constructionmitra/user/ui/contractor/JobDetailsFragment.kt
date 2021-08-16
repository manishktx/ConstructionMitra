package com.constructionmitra.user.ui.contractor

import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.constructionmitra.user.R
import com.constructionmitra.user.databinding.*
import com.constructionmitra.user.ui.contractor.viewmodels.JobDetailsViewModel
import com.constructionmitra.user.ui.work.WorkDetailsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_otp.*
import timber.log.Timber

@AndroidEntryPoint
class JobDetailsFragment : Fragment() {

    private var _binding: FragmentJobDetailsBinding? = null

    private val viewModel: JobDetailsViewModel by viewModels()

    // to manage the indicator state
    private val mIndicatorStates = hashMapOf<Int, Int>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJobDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        private const val DEFAULT_INDICATOR_TRANSITION = 200
    }
}