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
import com.constructionmitra.user.databinding.*
import com.constructionmitra.user.ui.contractor.viewmodels.JobDetailsViewModel

class JobRoleDetailsFragment : Fragment() {

    private var _binding: FragmentJobRoleDetailsBinding? = null

    private val jobDetailsViewModel: JobDetailsViewModel by lazy {
        ViewModelProvider(
            requireParentFragment().requireParentFragment()
        ).get(JobDetailsViewModel::class.java)
    }

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJobRoleDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            val items = resources.getStringArray(R.array.work_options)
            val adapter = ArrayAdapter(requireContext(), R.layout.item_drop_down, items)
            (textInput.editText as? AutoCompleteTextView)?.setAdapter(adapter)
            jobDetailsViewModel.onFragmentSelected(0)
        }

        jobDetailsViewModel.navigateToAddEmployeeDetails.observe(viewLifecycleOwner){
            JobRoleDetailsFragmentDirections.toAddEmployeeDetails().apply {
                findNavController().navigate(this)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}