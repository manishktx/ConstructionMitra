package com.constructionmitra.user.ui.employer.engineer_supervisor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.constructionmitra.user.R
import com.constructionmitra.user.data.JobRoleDetails
import com.constructionmitra.user.databinding.FragmentJobRoleDetailsEsBinding
import com.constructionmitra.user.databinding.ProgressBarBinding
import com.constructionmitra.user.ui.contractor.JobRoleDetailsPCFragmentDirections
import com.constructionmitra.user.ui.contractor.viewmodels.JobPostViewModel
import com.constructionmitra.user.utilities.AppUtils
import com.constructionmitra.user.utilities.constants.AppConstants
import com.constructionmitra.user.utilities.ext.app
import com.constructionmitra.user.utilities.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JobRoleDetailsESFragment : Fragment()  {

    private var selectedWorkItem: Int? = null
    private var selectedExpItem: Int? = null
    private var selectedQualificationItem: Int? = null
    private var selectedSalaryRangeItem: Int? = null
    private var workersRequiredInDays: String? = null
    private var _binding: FragmentJobRoleDetailsEsBinding? = null
    private lateinit var progressBarBinding: ProgressBarBinding
    private val appDataConfig by lazy { app().appConfigData }

    private val onWorkTypeItemSelectedListener: AdapterView.OnItemClickListener =
        AdapterView.OnItemClickListener {
                parent, view, position, id -> selectedWorkItem = position
        }

    private val onExpItemSelectedListener: AdapterView.OnItemClickListener =
        AdapterView.OnItemClickListener {
                parent, view, position, id -> selectedExpItem = position
        }


    private val onQualificationItemSelectedListener: AdapterView.OnItemClickListener =
        AdapterView.OnItemClickListener {
                parent, view, position, id -> selectedQualificationItem = position
        }

    private val onSalaryRangeSelectedListener: AdapterView.OnItemClickListener =
        AdapterView.OnItemClickListener {
                parent, view, position, id -> selectedSalaryRangeItem = position
        }

    private val viewModel: JobPostViewModel by lazy {
        ViewModelProvider(requireActivity()).get(JobPostViewModel::class.java)
    }

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJobRoleDetailsEsBinding.inflate(inflater, container, false).apply {
            progressBarBinding = ProgressBarBinding.bind(root)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onFragmentSelected(0)
        viewModel.selectedJobCategory?.categoryId?.let { categoryId ->
            showProgress(true)
            viewModel.requestJobRoles(categoryId)
        }
        registerObservers()
        bindDataWithUi()
    }

    private fun bindDataWithUi() {
        app().appConfigData?.let { configData ->
            val adapter = ArrayAdapter(requireContext(), R.layout.item_drop_down, configData.experiences)
            (binding.inputMinExp.editText as? AutoCompleteTextView)?.apply {
                onItemClickListener = this@JobRoleDetailsESFragment.onExpItemSelectedListener
                setAdapter(adapter)
            }

            val projectTypesAdapter = ArrayAdapter(requireContext(), R.layout.item_drop_down, configData.qualifications)
            (binding.inputMinQualification.editText as? AutoCompleteTextView)?.apply {
                onItemClickListener = this@JobRoleDetailsESFragment.onQualificationItemSelectedListener
                setAdapter(projectTypesAdapter)
            }

            val salaryRangesAdapter = ArrayAdapter(requireContext(), R.layout.item_drop_down, AppConstants.salaryRanges)
            (binding.inputSalary.editText as? AutoCompleteTextView)?.apply {
                onItemClickListener = this@JobRoleDetailsESFragment.onSalaryRangeSelectedListener
                setAdapter(salaryRangesAdapter)
            }
        }
    }

    private fun registerObservers() {
        viewModel.jobRoles.observe(viewLifecycleOwner) {
            showProgress(false)
            it?.takeIf { it.isNotEmpty() }?.let { items ->
                val adapter = ArrayAdapter(requireContext(), R.layout.item_drop_down, items)
                (binding.inputWork.editText as? AutoCompleteTextView)?.apply {
                    onItemClickListener = this@JobRoleDetailsESFragment.onWorkTypeItemSelectedListener
                    setAdapter(adapter)
                }
            } ?: run {
                // List is empty
                binding.root.showToast("Categories are empty!")
            }
        }

        viewModel.navigateToAddEmployeeDetails.observe(viewLifecycleOwner){
            if(isValidToProceed()){
                // save job details to view model
                viewModel.saveJobRoleDetails(
                    JobRoleDetails(
                        requiredDays = workersRequiredInDays ?: "",
                        _minQualificationId = appDataConfig?.getQualificationAt(selectedQualificationItem!!)?.qualificationId,
                        _minExpId = appDataConfig?.getExperienceAt(selectedExpItem!!)?.experienceId!!,
                        _gender = AppUtils.genderType(
                            (binding.root.findViewById(binding.rgGender.checkedRadioButtonId) as RadioButton).text.toString()
                        ),
                        _noOfOpenings = binding.etNumberOfPostOpening.text.toString(),
                        _salaryRange = selectedSalaryRangeItem?.let { it1 ->
                            AppConstants.salaryRanges[it1]
                        },
                        jobWorkId = viewModel.jobRoles.value?.get(selectedWorkItem!!)?.roleId,
                        workDesc = binding.etAdditionalInfo.text.toString(),
                        _classification = binding.etMinValue.text.toString(),
                    )
                )
                JobRoleDetailsPCFragmentDirections.toAddEmployeeDetails().apply {
                    findNavController().navigate(this)
                }
            }
            else {
                _binding?.root?.showToast(getString(R.string.all_fields_are_mandatory))
            }
        }
    }

    private fun isValidToProceed() =
        selectedExpItem != null
                && selectedQualificationItem != null
                && selectedSalaryRangeItem != null
                && binding.etNumberOfPostOpening.text.toString().trim().isNotEmpty()
                && binding.etMinValue.text.toString().trim().isNotBlank()
                && binding.etAdditionalInfo.text.toString().length > 40


    private fun showProgress(show: Boolean) {
        progressBarBinding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }


    companion object{
        private const val MIN_DAYS = "0 - 15"
        private const val MAX_DAYS = "15 - 30"
    }
}