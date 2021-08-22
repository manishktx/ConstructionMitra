package com.constructionmitra.user.ui.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.constructionmitra.user.R
import com.constructionmitra.user.data.AppPreferences
import com.constructionmitra.user.databinding.FragmentCompanyLetterHeadBinding
import com.constructionmitra.user.databinding.ProgressBarBinding
import com.constructionmitra.user.ui.PreviewImageActivity
import com.constructionmitra.user.ui.PreviewImageFragment
import com.constructionmitra.user.utilities.BitmapConfig
import com.constructionmitra.user.utilities.CMBitmapConfig
import com.constructionmitra.user.utilities.showToast
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class CompanyLetterHeadFragment : Fragment() {

    private lateinit var binding: FragmentCompanyLetterHeadBinding
    private lateinit var progressBarBinding: ProgressBarBinding

    private val viewModel: ProfileViewModel by viewModels()
    @Inject
    lateinit var appPreferences: AppPreferences

    private val bitmapConfig: BitmapConfig by lazy {
        CMBitmapConfig()
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
                showProgress(true)
                viewModel.fetchProfileInfo(appPreferences.getUserId()!!, appPreferences.getToken()!!)
                Timber.d("startActivityForResult called !")
                result.data?.let {
                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCompanyLetterHeadBinding.inflate(inflater, container, false).apply {
            progressBarBinding = ProgressBarBinding.bind(root)
            ivLetterHead.setOnClickListener {
                pickImageFromGallery()
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.galleryImageSaved.observe(viewLifecycleOwner) {
            it?.let {
                navigateToPreview(it)
            }
        }

        viewModel.profileUpdated.observe(viewLifecycleOwner){
            showProgress(false)
            if(it){

            }
        }
    }

    private fun navigateToPreview(it: File) {
        startActivityForResult.launch(
            Intent(requireContext(), PreviewImageActivity::class.java).apply {
                putExtra(PreviewImageActivity.FILE_PATH, it.absolutePath)
                putExtra(PreviewImageActivity.SAVE_WHERE, PreviewImageFragment.IN_WORK_CATALOGUE)
            }
        )
        requireActivity().overridePendingTransition(
            R.anim.enter_anim_activity,
            R.anim.exit_anim_activity
        )
    }

    private fun pickImageFromGallery() {
        pickImage.launch("image/*")
    }

    private fun showProgress(show: Boolean) {
        progressBarBinding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CompanyLetterHeadFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}