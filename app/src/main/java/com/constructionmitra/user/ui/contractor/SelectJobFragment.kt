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
import com.constructionmitra.user.databinding.FragmentSelectJobBinding
import com.constructionmitra.user.databinding.ProgressBarBinding
import com.constructionmitra.user.ui.contractor.viewmodels.JobPostViewModel
import com.constructionmitra.user.utilities.constants.Role
import com.constructionmitra.user.utilities.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_otp.*

@AndroidEntryPoint
class SelectJobFragment : Fragment() {

    private var selectedItem: Int? = null
    private var _binding: FragmentSelectJobBinding? = null
    private lateinit var progressBarBinding: ProgressBarBinding

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: JobPostViewModel by lazy {
        ViewModelProvider(requireActivity()).get(JobPostViewModel::class.java)
    }

    private val onItemSelectedListener: AdapterView.OnItemClickListener =
        AdapterView.OnItemClickListener {
                parent, view, position, id -> selectedItem = position
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectJobBinding.inflate(inflater, container, false).apply {
            progressBarBinding = ProgressBarBinding.bind(root)
            viewModel.clearData()
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            tvNext.setOnClickListener {
                if(binding.textInput.editText?.text.toString() !=
                    getString(R.string.select_your_job_role)
                ){
                    onNext(selectedItem)
                }
                else{

                }
            }
        }

        showProgress(true)
        viewModel.jobCategories()

        registerObservers()
    }

    private fun onNext(selectedItem: Int?) {
        selectedItem?.let {
            val selectedJob = viewModel.jobCategories.value?.get(it)!!
            viewModel.saveSelectedJob(selectedJob) // Save job for later
            viewModel.saveJobCategory(selectedJob.categoryId)
            with(selectedJob.category){
                when{
                    contains(Role.PETTY_CONTRACTOR.role, ignoreCase = true) -> {
                        SelectJobFragmentDirections.toSelectWorkFragment(
                            selectedJob.categoryId.toInt()
                        ).apply {
                            findNavController().navigate(this)
                        }
                    }
                    contains(Role.SPECIALISED_AGENCY.role, ignoreCase = true) -> {
                        findNavController().navigate(
                            SelectJobFragmentDirections.toJobDetailsFragment(Role.SPECIALISED_AGENCY, selectedJob.categoryId.toInt() )
                        )
                    }
                    contains(Role.ENGINEER_SUPERVISOR.role, ignoreCase = true) -> {
                        findNavController().navigate(
                            SelectJobFragmentDirections.toJobDetailsFragment(Role.ENGINEER_SUPERVISOR, selectedJob.categoryId.toInt())
                        )
                    }

                    else -> {}
                }
            }
        }
    }

    private fun registerObservers() {
        viewModel.jobCategories.observe(viewLifecycleOwner){
            showProgress(false)
            it?.takeIf { it.isNotEmpty() }?.let {
                    items ->
                val adapter = ArrayAdapter(requireContext(), R.layout.item_drop_down_center, items)
                (binding.textInput.editText as? AutoCompleteTextView)?.apply {
                    onItemClickListener = this@SelectJobFragment.onItemSelectedListener
                    setAdapter(adapter)
                }
            } ?: run {
                // List is empty
                binding.root.showToast("Categories are empty!")
            }
        }
    }

    private fun showProgress(show: Boolean) {
        progressBarBinding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
