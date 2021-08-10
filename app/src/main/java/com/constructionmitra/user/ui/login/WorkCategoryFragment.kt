package com.constructionmitra.user.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.constructionmitra.user.data.AppPreferences
import com.constructionmitra.user.data.dummyList
import com.constructionmitra.user.databinding.FragmentChooseWorkCategoryBinding
import com.constructionmitra.user.ui.login.adapters.CategoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WorkCategoryFragment : Fragment() {

    private lateinit var binding: FragmentChooseWorkCategoryBinding
    @Inject
    lateinit var appPreferences: AppPreferences
    val args: WorkCategoryFragmentArgs by navArgs()

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
        binding = FragmentChooseWorkCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // bind data with UI
        binding.tvMobileNumb.text = "+91".plus(args.mobile)

        binding.rvCategories.adapter = CategoryAdapter(dummyList) {
            WorkCategoryFragmentDirections.toWorkSubCategories("2").apply {
                findNavController().navigate(this)
            }
        }
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