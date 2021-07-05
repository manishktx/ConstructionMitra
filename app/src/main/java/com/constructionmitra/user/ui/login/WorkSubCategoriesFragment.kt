package com.constructionmitra.user.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.constructionmitra.user.MainActivity
import com.constructionmitra.user.R
import com.constructionmitra.user.data.dummyListSubCategories
import com.constructionmitra.user.databinding.FragmentChooseYourWorkSubCategoriesBinding
import com.constructionmitra.user.ui.dialogs.AppAlertDialog
import com.constructionmitra.user.ui.login.adapters.WorkCategoryAdapter

class WorkSubCategoriesFragment : Fragment() {

    private lateinit var binding: FragmentChooseYourWorkSubCategoriesBinding

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
        binding = FragmentChooseYourWorkSubCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvSubCategories.adapter = WorkCategoryAdapter(dummyListSubCategories, isSubCategory = true) {
            AppAlertDialog.newInstance {
                navigateToHome()
            }.show(childFragmentManager, "alert_dialog")
        }
    }

    private fun navigateToHome(){
        Intent(context, MainActivity::class.java).apply {
            requireContext().startActivity(this)
        }
        requireActivity().overridePendingTransition(R.anim.enter_anim_activity, R.anim.exit_anim_activity)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WorkSubCategoriesFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}