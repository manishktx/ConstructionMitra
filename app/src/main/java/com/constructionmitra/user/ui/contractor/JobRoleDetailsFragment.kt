package com.constructionmitra.user.ui.contractor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.constructionmitra.user.R
import com.constructionmitra.user.adapters.SelectWorkAdapter
import com.constructionmitra.user.data.JobRoleDetails
import com.constructionmitra.user.databinding.*
import com.constructionmitra.user.ui.contractor.viewmodels.JobPostViewModel
import com.constructionmitra.user.utilities.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.card_job_roles.view.*
import kotlinx.android.synthetic.main.view_list_job_roles.view.*

@AndroidEntryPoint
class JobRoleDetailsFragment : Fragment() {

    private var selectedItem: Int? = null
    private var _binding: FragmentJobRoleDetailsBinding? = null
    private lateinit var progressBarBinding: ProgressBarBinding

    private val jobPostViewModel: JobPostViewModel by lazy {
        ViewModelProvider(
            requireActivity()
        ).get(JobPostViewModel::class.java)
    }

    private val onItemSelectedListener: AdapterView.OnItemClickListener =
        AdapterView.OnItemClickListener {
                parent, view, position, id -> selectedItem = position
        }

    private val viewModel: JobPostViewModel by lazy {
        ViewModelProvider(requireActivity()).get(JobPostViewModel::class.java)
    }

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJobRoleDetailsBinding.inflate(inflater, container, false).apply {
            progressBarBinding = ProgressBarBinding.bind(root)
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            val items = resources.getStringArray(R.array.work_options)
            val adapter = ArrayAdapter(requireContext(), R.layout.item_drop_down, items)
            (textInput.editText as? AutoCompleteTextView)?.setAdapter(adapter)
            jobPostViewModel.onFragmentSelected(0)
        }
        // fetch project types
        showProgress(true)
        viewModel.getProjectTypes()

        // Bind selected work data
        setAddWorkAdapter()
        registerObservers()

    }

    private fun registerObservers() {
        jobPostViewModel.navigateToAddEmployeeDetails.observe(viewLifecycleOwner){
            // save details and navigate to Employee details
            if(areDetailsValid() && selectedItem != null){
                viewModel.saveJobRoleDetails(
                    JobRoleDetails(
                        binding.etMinDuration.text.toString(),
                        viewModel.projectTypes.value?.get(selectedItem!!)?.projectTypeId!!,
                        binding.etWorkDesc.text.toString()
                    )
                )
                JobRoleDetailsFragmentDirections.toAddEmployeeDetails().apply {
                    findNavController().navigate(this)
                }
            }
            else{
                binding.root.showToast("Please provide all details")
            }
        }

        viewModel.projectTypes.observe(viewLifecycleOwner){
            showProgress(false)
            it?.takeIf { it.isNotEmpty() }?.let {
                    items ->
                val adapter = ArrayAdapter(requireContext(), R.layout.item_drop_down_center, items)
                (binding.textInput.editText as? AutoCompleteTextView)?.apply {
                    onItemClickListener = this@JobRoleDetailsFragment.onItemSelectedListener
                    setAdapter(adapter)
                }
            } ?: run {
                // List is empty
                binding.root.showToast("Categories are empty!")
            }
        }
    }

    private fun setAddWorkAdapter() {
        with(binding.cardJobRoles.viewJobRoles.layoutJobRolesContainer){
            visibility = View.VISIBLE
            rvJobRoles.adapter = SelectWorkAdapter(
                viewModel.selectWorkDataList!!
            )
        }
    }

    private fun areDetailsValid() = selectedItem != null && binding.etWorkDesc.text.toString().trimEnd().length > 10 &&
            binding.etMinDuration.text.toString().trimEnd().isNotEmpty()

    private fun showProgress(show: Boolean) {
        progressBarBinding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}