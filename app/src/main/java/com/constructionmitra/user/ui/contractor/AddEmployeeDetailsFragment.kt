package com.constructionmitra.user.ui.contractor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.constructionmitra.user.R
import com.constructionmitra.user.databinding.FragmentAddEmployeeDetailsBinding
import com.constructionmitra.user.databinding.FragmentJobRoleDetailsBinding
import com.constructionmitra.user.databinding.FragmentSelectJobBinding
import com.constructionmitra.user.ui.contractor.viewmodels.JobDetailsViewModel
import kotlinx.android.synthetic.main.fragment_otp.*

class AddEmployeeDetailsFragment : Fragment() {

    private var _binding: FragmentAddEmployeeDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val jobDetailsViewModel: JobDetailsViewModel by lazy {
        ViewModelProvider(
            requireParentFragment().requireParentFragment()
        ).get(JobDetailsViewModel::class.java)
    }

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().popBackStack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddEmployeeDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            backPressedCallback
        )
        jobDetailsViewModel.onFragmentSelected(1)
        with(binding){
        }

        jobDetailsViewModel.navigateToReviewJob.observe(viewLifecycleOwner){
            AddEmployeeDetailsFragmentDirections.toReviewYourJobFragment().apply {
                findNavController().navigate(this)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}