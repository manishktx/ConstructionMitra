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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.constructionmitra.user.R
import com.constructionmitra.user.adapters.SelectWorkAdapter
import com.constructionmitra.user.data.AppPreferences
import com.constructionmitra.user.data.JobPostId
import com.constructionmitra.user.data.SelectWorkData
import com.constructionmitra.user.databinding.FragmentSelectJobBinding
import com.constructionmitra.user.databinding.FragmentSelectWorkBinding
import com.constructionmitra.user.databinding.ProgressBarBinding
import com.constructionmitra.user.ui.contractor.viewmodels.JobPostViewModel
import com.constructionmitra.user.utilities.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_otp.*
import javax.inject.Inject

@AndroidEntryPoint
class SelectWorkFragment : Fragment() {

    private var jobPostId: JobPostId? = null
    private var selectedItem: Int? = null
    private var selectWorkAdapter: SelectWorkAdapter? = null
    private lateinit var progressBarBinding: ProgressBarBinding
    private var _binding: FragmentSelectWorkBinding? = null
    @Inject
    lateinit var appPreferences: AppPreferences

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val args: SelectWorkFragmentArgs by navArgs()

    private val viewModel: JobPostViewModel by lazy {
        ViewModelProvider(
            requireActivity()
        ).get(JobPostViewModel::class.java)
    }

    private val onItemSelectedListener: AdapterView.OnItemClickListener =
        AdapterView.OnItemClickListener {
                parent, view, position, id -> selectedItem = position
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectWorkBinding.inflate(inflater, container, false).apply {
            progressBarBinding = ProgressBarBinding.bind(root)
            tvAddJob.setOnClickListener {
                // Add job role
                if(selectedItem != null && binding.etNumOfWorker.text.toString().trimEnd().isNotEmpty()){
                    showProgress(true)
                    viewModel.addJobWork(
                        userId = appPreferences.getUserId(),
                        jobCategoryId =  args.categoryId.toString(),
                        jobPostId = jobPostId?.jobPostId ?: "1",
                        jobRoleId = viewModel.jobRoles.value?.get(selectedItem!!)?.roleId!!,
                        numOfWorker =  binding.etNumOfWorker.text.toString()
                    )
                }
                else {
                    binding.root.showToast("Please fill details!")
                }
            }
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            // Hide showAddedJobRole view
            addJobRolesLayout.layoutJobRolesContainer.visibility = View.GONE
            tvContinue.setOnClickListener {
                if(selectedItem != null){
                    viewModel.updateSelectedWorkList(selectWorkAdapter?.allWorkList()!!)
                    SelectWorkFragmentDirections.toJobDetailsFragment().apply {
                        findNavController().navigate(this)
                    }
                }
                else
                    binding.root.showToast("Please add atleast 1 job to proceed!")
            }
        }
        showProgress(true)
        viewModel.requestJobRoles(jobCategory =  args.categoryId.toString())

        registerObservers()
    }

    private fun registerObservers() {
        viewModel.jobRoles.observe(viewLifecycleOwner){
            showProgress(false)
            it?.takeIf { it.isNotEmpty() }?.let {
                    items ->
                val adapter = ArrayAdapter(requireContext(), R.layout.item_drop_down_center, items)
                (binding.textInput.editText as? AutoCompleteTextView)?.apply {
                    onItemClickListener = this@SelectWorkFragment.onItemSelectedListener
                    setAdapter(adapter)
                }
            } ?: run {
                // List is empty
                binding.root.showToast("Categories are empty!")
            }
        }

        // Work added
        viewModel.jobPostId.observe(viewLifecycleOwner){
            it?.let {
                jobPostId = it
                // Add current job post to recycler view
                showProgress(false)
                selectWorkAdapter?.takeIf { true }?.addWork(currentWork()) ?: run {
                    setAddWorkAdapter()
                }
            }
        }
    }

    private fun setAddWorkAdapter() {
        with(binding){
            addJobRolesLayout.layoutJobRolesContainer.visibility = View.VISIBLE
            addJobRolesLayout.rvJobRoles.adapter = SelectWorkAdapter(
                mutableListOf(currentWork())
            ).apply {
                selectWorkAdapter = this
                clearSelection()
            }
        }
    }

    private fun clearSelection(){
        binding.etNumOfWorker.setText("")
    }

    private fun currentWork(): SelectWorkData{
        return SelectWorkData(
            viewModel.jobRoles.value?.get(selectedItem!!)?.jobRole!!,
            binding.etNumOfWorker.text.toString()
        )
    }

    private fun showProgress(show: Boolean) {
        progressBarBinding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}