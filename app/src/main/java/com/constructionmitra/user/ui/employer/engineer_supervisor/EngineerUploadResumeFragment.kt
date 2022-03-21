package com.constructionmitra.user.ui.employer.engineer_supervisor

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.constructionmitra.user.R
import com.constructionmitra.user.api.ProfileRequests
import com.constructionmitra.user.data.AppPreferences
import com.constructionmitra.user.databinding.FragmentAboutBinding
import com.constructionmitra.user.databinding.FragmentEngineerUploadResumeBinding
import com.constructionmitra.user.databinding.ProgressBarBinding
import com.constructionmitra.user.ui.PreviewImageActivity
import com.constructionmitra.user.ui.PreviewImageFragment
import com.constructionmitra.user.ui.profile.AboutFragment
import com.constructionmitra.user.ui.profile.ProfileViewModel
import com.constructionmitra.user.utilities.BitmapConfig
import com.constructionmitra.user.utilities.CMBitmapConfig
import com.constructionmitra.user.utilities.showToast
import dagger.hilt.android.AndroidEntryPoint

import java.io.File
import javax.inject.Inject


@AndroidEntryPoint
class EngineerUploadResumeFragment : Fragment() {
    private lateinit var binding: FragmentEngineerUploadResumeBinding
    private lateinit var progressBarBinding: ProgressBarBinding
    @Inject
    lateinit var appPreferences: AppPreferences
    @Inject
    lateinit var profileRequests: ProfileRequests
    private var isProfileUpdated = false

    private val viewModel: ProfileViewModel by viewModels()

    private val bitmapConfig: BitmapConfig by lazy {
        CMBitmapConfig()
    }

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if(isProfileUpdated)
                requireActivity().setResult(AppCompatActivity.RESULT_OK)
            requireActivity().finish()
        }
    }

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
        it?.let { imageUri ->
            // Decode and save image code
            viewModel.decodeAndSaveGalleryImage(
                imageUri,
                bitmapConfig,
                requireContext()
            )
        }
    }

    private val startActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // todo:: hit fetch user details and update image UI
//                showProgress(true)
//                viewModel.fetchProfileInfo(appPreferences.getUserId()!!, appPreferences.getToken()!!)
//                Timber.d("startActivityForResult called !")
//                result.data?.let {
//                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backPressedCallback)
        binding = FragmentEngineerUploadResumeBinding.inflate(inflater, container, false).apply {
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


        // set listener
        binding.btnSearchJob.setOnClickListener {

        }

        binding.btnUploadResume.setOnClickListener {
            pickImageFromGallery()
        }

        registerObservers(); onError()
    }

    private fun registerObservers() {
//        viewModel.profileUpdated.observe(viewLifecycleOwner){
//            showProgress(false)
//            it?.let {
//                if(it){
//                    isProfileUpdated = true
//                    binding.root.showToast("ProfileUpdated!")
//                    requireActivity().setResult(AppCompatActivity.RESULT_OK)
//                    requireActivity().finish()
//                }
//            }
//        }

        viewModel.galleryImageSaved.observe(viewLifecycleOwner){
            it?.let {
                navigateToPreview(it)
            }
        }
    }

    private fun pickImageFromGallery() {
        pickImage.launch("file/*")
    }

    private fun navigateToPreview(it: File) {
        startActivityForResult.launch(
            Intent(requireContext(), PreviewImageActivity::class.java).apply {
                putExtra(PreviewImageActivity.FILE_PATH, it.absolutePath)
                putExtra(PreviewImageFragment.SAVE_WHERE, PreviewImageFragment.PROFILE_IMAGE)
            }
        )
        requireActivity().overridePendingTransition(
            R.anim.enter_anim_activity,
            R.anim.exit_anim_activity
        )
    }

    private fun onError() {
        viewModel.errorMsg.observe(viewLifecycleOwner){
            showProgress(false)
            binding.root.showToast(it!!)
        }
    }
    private fun showProgress(show: Boolean) {
        progressBarBinding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            EngineerUploadResumeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}