package com.constructionmitra.user.ui.contractor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.constructionmitra.user.R
import com.constructionmitra.user.databinding.FragmentJobRoleDetailsBinding
import com.constructionmitra.user.databinding.FragmentPostedJobsBinding
import com.constructionmitra.user.databinding.FragmentSelectJobBinding
import com.constructionmitra.user.ui.contractor.viewmodels.UiViewModel
import kotlinx.android.synthetic.main.fragment_otp.*

class PostedJobsFragment : Fragment() {

    private var _binding: FragmentPostedJobsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val uiViewModel: UiViewModel by lazy {
        ViewModelProvider(requireActivity()).get(UiViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostedJobsBinding.inflate(inflater, container, false).apply {
            tvPostAJob.setOnClickListener {
                uiViewModel.initJobPostProcess(true)
            }
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(_binding){

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}