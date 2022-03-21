package com.constructionmitra.user.ui.employer.engineer_supervisor

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.RadioButton
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.constructionmitra.user.R
import com.constructionmitra.user.api.ProfileRequests
import com.constructionmitra.user.data.*
import com.constructionmitra.user.databinding.FragmentAboutBinding
import com.constructionmitra.user.databinding.FragmentEngineerExperianceDetailsBinding
import com.constructionmitra.user.databinding.ProgressBarBinding
import com.constructionmitra.user.ui.PreviewImageActivity
import com.constructionmitra.user.ui.PreviewImageFragment
import com.constructionmitra.user.ui.profile.AboutFragment
import com.constructionmitra.user.ui.profile.ProfileViewModel
import com.constructionmitra.user.utilities.AppUtils
import com.constructionmitra.user.utilities.constants.AppConstants
import com.constructionmitra.user.utilities.showToast
import kotlinx.android.synthetic.main.fragment_engineer_home.*
import timber.log.Timber
import java.io.File
import javax.inject.Inject


class EngineerExperienceDetailFragment : Fragment() {

    private lateinit var binding: FragmentEngineerExperianceDetailsBinding
    private lateinit var progressBarBinding: ProgressBarBinding
    @Inject
    lateinit var appPreferences: AppPreferences
    @Inject
    lateinit var profileRequests: ProfileRequests

    private var isProfileUpdated = false

    private val viewModel: ProfileViewModel by viewModels()

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if(isProfileUpdated)
                requireActivity().setResult(AppCompatActivity.RESULT_OK)
            requireActivity().finish()
        }
    }


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
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backPressedCallback)
        binding = FragmentEngineerExperianceDetailsBinding.inflate(inflater, container, false).apply {
            progressBarBinding = ProgressBarBinding.bind(root)
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgress(true)
        lifecycleScope.launchWhenResumed {
            viewModel.fetchProfileInfo(appPreferences.getUserId()!!, appPreferences.getToken()!!)
        }
        viewModel.profileData.observe(viewLifecycleOwner){
            showProgress(false)
            it?.let {
//                bindData(it)
            }
        }

        // set listener
        binding.btnSave.setOnClickListener {
            getExperienceData()?.let {
                Timber.d("Engineer Experience data = $it")
                showProgress(true)
                viewModel.updateProfile(profileRequests.updateExpEngineer(
                    userId = appPreferences.getUserId(),
                    token = appPreferences.getToken()!!,
                    aboutData = it
                ))
            }
        }


        registerObservers(); onError()
    }

    private fun registerObservers() {
        viewModel.profileUpdated.observe(viewLifecycleOwner){
            showProgress(false)
            it?.let {
                if(it){
                    isProfileUpdated = true
                    binding.root.showToast("ProfileUpdated!")
                    requireActivity().setResult(AppCompatActivity.RESULT_OK)
                    requireActivity().finish()
                }
            }
        }


    }



    private fun onError() {
        viewModel.errorMsg.observe(viewLifecycleOwner){
            showProgress(false)
            binding.root.showToast(it!!)
        }
    }

    private fun getExperienceData(): EngineerAboutData?{
        // check validation for mobile and name
        val aboutData = EngineerAboutData()
        binding.txtDesignation.text?.let {
            if(it.toString().trim().length < 10){
                binding.root.showToast(getString(R.string.enter_valid_mobile_num))
                return@getExperienceData null
            }
            aboutData.present_designation = it.toString()
        }


        if(binding.txtCurrComp.text!!.isEmpty()){
            binding.root.showToast(getString(R.string.enter_valid_name))
            return null
        }


        with(binding){


            etCurrComp.text?.let {
                aboutData.present_employer  = it.toString()
            }
            etCurrentResponsibility.text?.let {
                aboutData.current_resposibility  = it.toString()
            }
        }

        return aboutData

    }



    private fun showProgress(show: Boolean) {
        progressBarBinding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            EngineerExperienceDetailFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

}