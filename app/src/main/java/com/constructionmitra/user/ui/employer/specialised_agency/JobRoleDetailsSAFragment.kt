package com.constructionmitra.user.ui.employer.specialised_agency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.constructionmitra.user.R
import com.constructionmitra.user.data.ConfigData
import com.constructionmitra.user.data.JobRoleDetails
import com.constructionmitra.user.databinding.FragmentJobRoleDetailsSaBinding
import com.constructionmitra.user.databinding.ProgressBarBinding
import com.constructionmitra.user.ui.contractor.JobRoleDetailsPCFragmentDirections
import com.constructionmitra.user.ui.contractor.viewmodels.JobPostViewModel
import com.constructionmitra.user.utilities.ext.app
import com.constructionmitra.user.utilities.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JobRoleDetailsSAFragment : Fragment() {

    private var selectedWorkItem: Int? = null
    private var selectedExpItem: Int? = null
    private var selectedProjectItem: Int? = null
    private var workersRequiredInDays: String? = null
    private var _binding: FragmentJobRoleDetailsSaBinding? = null
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


    private val onProjectTypeItemSelectedListener: AdapterView.OnItemClickListener =
        AdapterView.OnItemClickListener {
                parent, view, position, id -> selectedProjectItem = position
        }


    private val viewModel: JobPostViewModel by lazy {
        ViewModelProvider(requireActivity()).get(JobPostViewModel::class.java)
    }

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJobRoleDetailsSaBinding.inflate(inflater, container, false).apply {
            progressBarBinding = ProgressBarBinding.bind(root)
            tvMinDays.text = getString(R.string.workers_required_in_days, MIN_DAYS)
            tvMaxDays.text = getString(R.string.workers_required_in_days, MAX_DAYS)
            tvMinDays.setOnClickListener {
                workersRequiredInDays  = MIN_DAYS
                makeSelectedView(it as AppCompatTextView)
                makeUnSelectedView(tvMaxDays)
            }

            tvMaxDays.setOnClickListener {
                workersRequiredInDays  = MAX_DAYS
                makeSelectedView(it as AppCompatTextView)
                makeUnSelectedView(tvMinDays)
            }
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
        bindDataWithUi()
        registerObservers()
    }

    private fun bindDataWithUi() {
        app().appConfigData?.let { configData ->
            val adapter = ArrayAdapter(requireContext(), R.layout.item_drop_down, configData.experiences)
            (binding.inputMinExp.editText as? AutoCompleteTextView)?.apply {
                onItemClickListener = this@JobRoleDetailsSAFragment.onExpItemSelectedListener
                setAdapter(adapter)
            }

            val projectTypesAdapter = ArrayAdapter(requireContext(), R.layout.item_drop_down, configData.projectType)
            (binding.projectTypesInput.editText as? AutoCompleteTextView)?.apply {
                onItemClickListener = this@JobRoleDetailsSAFragment.onProjectTypeItemSelectedListener
                setAdapter(projectTypesAdapter)
            }
        }
    }

    private fun registerObservers() {
        viewModel.jobRoles.observe(viewLifecycleOwner) {
            showProgress(false)
            it?.takeIf { it.isNotEmpty() }?.let { items ->
                val adapter = ArrayAdapter(requireContext(), R.layout.item_drop_down, items)
                (binding.jobRoleInput.editText as? AutoCompleteTextView)?.apply {
                    onItemClickListener = this@JobRoleDetailsSAFragment.onWorkTypeItemSelectedListener
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
                        jobWorkId = viewModel.jobRoles.value?.get(selectedWorkItem!!)?.roleId,
                        projectId = appDataConfig?.getProjectAt(selectedProjectItem!!)?.projectTypeId!!,
                        workDesc = binding.etWorkDesc.text.toString(),
                        _minExpId = appDataConfig?.getExperienceAt(selectedExpItem!!)?.experienceId!!,
                        _workDoneEarlier = binding.etTypeOfWork.text.toString(),
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

    private fun makeSelectedView(view: AppCompatTextView){
        view.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_button_blue_selected)
        view.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
    }

    private fun makeUnSelectedView(view: AppCompatTextView){
        view.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_button_blue_unselected)
        view.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue_300))
    }

    private fun isValidToProceed()  = selectedWorkItem != null
            && selectedProjectItem != null
            && selectedExpItem != null
            && workersRequiredInDays != null
            && binding.etMinValue.text.toString().trim().isNotBlank()
            && binding.etWorkDesc.text.toString().trim().isNotBlank()
            && binding.etTypeOfWork.text.toString().trim().isNotBlank()

    private fun showProgress(show: Boolean) {
        progressBarBinding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    companion object{
        private const val MIN_DAYS = "0 - 15"
        private const val MAX_DAYS = "15 - 30"
    }
}