package com.constructionmitra.user.ui.contractor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.constructionmitra.user.R
import com.constructionmitra.user.data.EmployeeDetails
import com.constructionmitra.user.databinding.FragmentAddEmployeeDetailsBinding
import com.constructionmitra.user.databinding.ProgressBarBinding
import com.constructionmitra.user.ui.contractor.viewmodels.JobPostViewModel
import com.constructionmitra.user.utilities.AppUtils
import com.constructionmitra.user.utilities.showToast
import kotlinx.android.synthetic.main.fragment_request_for_work.*

class AddEmployeeDetailsFragment : Fragment() {

    private var selectedItem: Int? = null
    private var _binding: FragmentAddEmployeeDetailsBinding? = null
    private lateinit var progressBarBinding: ProgressBarBinding

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val jobPostViewModel: JobPostViewModel by lazy {
        ViewModelProvider(
            requireActivity()
        ).get(JobPostViewModel::class.java)
    }

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().popBackStack()
        }
    }

    private val onItemSelectedListener: AdapterView.OnItemClickListener =
        AdapterView.OnItemClickListener {
                parent, view, position, id -> selectedItem = position
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddEmployeeDetailsBinding.inflate(inflater, container, false).apply {
            progressBarBinding = ProgressBarBinding.bind(root)
        }
        lifecycleScope.launchWhenResumed {
            showProgress(true)
            jobPostViewModel.getActiveLocations()
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            backPressedCallback
        )
        jobPostViewModel.onFragmentSelected(1)
        with(binding){
        }

        jobPostViewModel.navigateToReviewJob.observe(viewLifecycleOwner){
            // Save employee details and navigate to review jobPost Screen
            if(areDetailsValid()){
                jobPostViewModel.saveEmployeeDetails(
                    employeeDetails =  EmployeeDetails(
                        companyName = binding.etCompanyName.text.toString(),
                        contactPerson = binding.etContactPersonName.text.toString(),
                        mobileNumber = binding.etMobileNum.text.toString(),
                        emailId = binding.etEmail.text.toString(),
                        projectName = binding.etProjectName.text.toString(),
                        projectLocation = jobPostViewModel.activeLocations.value?.get(selectedItem!!)?.name!!,
                        projectLocationId = jobPostViewModel.activeLocations.value?.get(selectedItem!!)?.id!!,
                        designation = binding.etDesignation.text.toString(),
                    )
                )
                AddEmployeeDetailsFragmentDirections.toReviewYourJobFragment().apply {
                    findNavController().navigate(this)
                }
            }
            else{

            }
        }

        registerObservers()
    }

    private fun registerObservers() {
        jobPostViewModel.activeLocations.observe(viewLifecycleOwner){
            showProgress(false)
            it?.takeIf { it.isNotEmpty() }?.let {
                    items ->
                val adapter = ArrayAdapter(requireContext(), R.layout.item_drop_down_center, items)
                (binding.textInput.editText as? AutoCompleteTextView)?.apply {
                    onItemClickListener = this@AddEmployeeDetailsFragment.onItemSelectedListener
                    setAdapter(adapter)
                }
            } ?: run {
                // List is empty
                binding.root.showToast("Categories are empty!")
            }
        }
    }

    private fun areDetailsValid(): Boolean{
        with(binding){
            if(etCompanyName.text.toString().trimEnd().isEmpty()){
                 return false
            }

            if(etContactPersonName.text.toString().trimEnd().isEmpty()){
                return false
            }

            if(etCompanyName.text.toString().trimEnd().isEmpty()){
                return false
            }

            if(!AppUtils.isValidMobile(etMobileNum.text.toString())){
                binding.root.showToast(getString(R.string.enter_valid_mobile_num))
                return false
            }

            if(!AppUtils.isEmailValid(etEmail.text.toString())){
                binding.root.showToast(getString(R.string.enter_valid_email_address))
                return false
            }

            if(selectedItem == null){
                return false
            }
        }

        return true
    }

    private fun showProgress(show: Boolean) {
        progressBarBinding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}