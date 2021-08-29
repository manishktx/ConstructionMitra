package com.constructionmitra.user.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.constructionmitra.user.data.AppPreferences
import com.constructionmitra.user.data.dummyList
import com.constructionmitra.user.databinding.FragmentChooseWorkCategoryBinding
import com.constructionmitra.user.databinding.ProgressBarBinding
import com.constructionmitra.user.ui.login.adapters.CategoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WorkCategoryFragment : Fragment() {

    private lateinit var binding: FragmentChooseWorkCategoryBinding
    private lateinit var progressBarBinding: ProgressBarBinding
    @Inject
    lateinit var appPreferences: AppPreferences
    private val args: WorkCategoryFragmentArgs by navArgs()

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChooseWorkCategoryBinding.inflate(inflater, container, false).apply {
            progressBarBinding = ProgressBarBinding.bind(root)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // bind data with UI
        binding.tvMobileNumb.text = "+91".plus(args.mobile)
        showProgress(true)
        viewModel.jobCategories()


        viewModel.jobCategories.observe(viewLifecycleOwner){
            showProgress(false)
            it?.let {
                _categories ->
                binding.rvCategories.adapter = CategoryAdapter(_categories) {
                    WorkCategoryFragmentDirections.toWorkSubCategories(it.categoryId).apply {
                        findNavController().navigate(this)
                    }
                }
            }
        }
    }

    private fun showProgress(show: Boolean) {
        progressBarBinding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WorkCategoryFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}