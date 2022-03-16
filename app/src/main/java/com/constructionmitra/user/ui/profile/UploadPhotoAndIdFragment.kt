package com.constructionmitra.user.ui.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.constructionmitra.user.R
import com.constructionmitra.user.databinding.FragmentAboutBinding
import com.constructionmitra.user.databinding.FragmentUploadPhotoIdBinding
import com.constructionmitra.user.ui.PreviewImageActivity
import com.constructionmitra.user.ui.PreviewImageFragment
import com.constructionmitra.user.utilities.BitmapConfig
import com.constructionmitra.user.utilities.CMBitmapConfig
import java.io.File


class UploadPhotoAndIdFragment : Fragment() {

    private lateinit var binding: FragmentUploadPhotoIdBinding

    private val viewModel: ProfileViewModel by viewModels()

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
                // todo: Get value from pref and show URL
//                showProgress(true)
//                viewModel.fetchProfileInfo(appPreferences.getUserId()!!, appPreferences.getToken()!!)
//                Timber.d("startActivityForResult called !")
//                result.data?.let {
//                }
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
        binding = FragmentUploadPhotoIdBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun pickImageFromGallery() {
        pickImage.launch("image/*")
    }

    private fun registerObserver(){
        viewModel.galleryImageSaved.observe(viewLifecycleOwner){
            it?.let {
                navigateToPreview(it)
            }
        }
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


    companion object {

        @JvmStatic
        fun newInstance() =
            UploadPhotoAndIdFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}