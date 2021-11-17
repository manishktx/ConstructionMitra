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
import com.constructionmitra.user.utilities.AppUtils
import com.constructionmitra.user.utilities.showToast
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.FadeInAnimator
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
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
        AdapterView.OnItemClickListener { parent, view, position, id ->
            selectedItem = position
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSelectWorkBinding.inflate(inflater, container, false).apply {
            progressBarBinding = ProgressBarBinding.bind(root)
            tvAddJob.setOnClickListener {

                // Add job role
                if (isUserValidToAddJobWork()) {
                    AppUtils.hideSoftKeyboard(requireActivity())
                    showProgress(true)
                    viewModel.addJobWork(
                        userId = appPreferences.getUserId(),
                        jobCategoryId = args.categoryId.toString(),
                        jobPostId = jobPostId?.jobPostId ?: "0",
                        jobRoleId = viewModel.jobRoles.value?.get(selectedItem!!)?.roleId!!,
                        numOfWorker = binding.etNumOfWorker.text.toString()
                    )
                }
            }
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            // Check if viewModel has selectedJobRoles then display else hide jobRolesContainer view
            if(viewModel.jobPostRequest?.selectedWorkList != null){
                addJobRolesLayout.layoutJobRolesContainer.visibility = View.VISIBLE
                initWorkAdapter(viewModel.jobPostRequest?.selectedWorkList!!)
            }
            else
                addJobRolesLayout.layoutJobRolesContainer.visibility = View.GONE

            // Set click listener
            tvContinue.setOnClickListener {
                if (areDetailsValid()) {
                    selectWorkAdapter?.allWorkList()?.let {
                        viewModel.updateSelectedWorkList(it)
                    }
                    SelectWorkFragmentDirections.toJobDetailsFragment(categoryId =  args.categoryId).apply {
                        findNavController().navigate(this)
                    }
                } else
                    binding.root.showToast("Please add at least 1 job to proceed!")
            }
        }
        showProgress(true)
        viewModel.requestJobRoles(jobCategory = args.categoryId.toString())

        registerObservers()
    }

    private fun registerObservers() {
        viewModel.jobRoles.observe(viewLifecycleOwner) {
            showProgress(false)
            it?.takeIf { it.isNotEmpty() }?.let { items ->
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
        viewModel.jobPostId.observe(viewLifecycleOwner) {
            it?.let {
                jobPostId = it
                // Add current job post to recycler view
                showProgress(false)
                selectWorkAdapter?.takeIf { true }?.addWork(currentWork(jobPostId?.jobWorkId?.toInt()!!)) ?: run {
                    initWorkAdapter(jobPostId!!)
                }
            }
        }

        // Work deleted
        viewModel.jobDeleted.observe(viewLifecycleOwner) { _jobWorkId ->
            showProgress(false)
            selectWorkAdapter?.let {
                it.removeItem(_jobWorkId)
            }
        }
    }

    private fun initWorkAdapter(list: List<SelectWorkData>) {
        with(binding) {
            addJobRolesLayout.layoutJobRolesContainer.visibility = View.VISIBLE
            addJobRolesLayout.rvJobRoles.itemAnimator = FadeInAnimator()
            addJobRolesLayout.rvJobRoles.adapter = SelectWorkAdapter(
                list as MutableList<SelectWorkData>,
                true
            ) {
                // Delete Item
                position, selectWorkData ->
                deleteJobWork(position, selectWorkData)

            }.apply {
                selectWorkAdapter = this
                resetUi()
            }
        }
    }

    private fun initWorkAdapter(jobPostId: JobPostId) {
        with(binding) {
            addJobRolesLayout.layoutJobRolesContainer.visibility = View.VISIBLE
            addJobRolesLayout.rvJobRoles.itemAnimator = FadeInAnimator()
            addJobRolesLayout.rvJobRoles.adapter = SelectWorkAdapter(
                mutableListOf(currentWork(jobPostId.jobWorkId)),
                true
            ) {
                // Delete Item
                    position, selectWorkData ->
                    deleteJobWork(position, selectWorkData)

            }.apply {
                selectWorkAdapter = this
                resetUi()
            }
        }
    }

    private fun deleteJobWork(position: Int, selectWorkData: SelectWorkData){
        showProgress(true)
        viewModel.deleteJobWork(selectWorkData.jobWorkId)
    }

    private fun resetUi() {
        binding.etNumOfWorker.setText("")
    }

    private fun areDetailsValid() = selectedItem != null && selectWorkAdapter != null && selectWorkAdapter?.allWorkList()?.size!! > 0

    private fun isUserValidToAddJobWork(): Boolean{
        if(selectedItem != null && binding.etNumOfWorker.text.toString().trimEnd()
                .isNotEmpty())
        {} else {
            binding.root.showToast("Please fill details!")
            return false
        }
        selectWorkAdapter?.let {
            if(it.allWorkList().size == 3 ){
                binding.root.showToast(getString(R.string.max_job_work_limit_reached))
                return false
            }
        }
        return true
    }

    private fun currentWork(jobWorkId: Int): SelectWorkData {
        return SelectWorkData(
            viewModel.jobRoles.value?.get(selectedItem!!)?.jobRole!!,
            binding.etNumOfWorker.text.toString(),
            jobWorkId
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