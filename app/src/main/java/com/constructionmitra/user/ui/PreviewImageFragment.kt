package com.constructionmitra.user.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.constructionmitra.user.R
import com.constructionmitra.user.data.AppPreferences
import com.constructionmitra.user.databinding.FragmentPreviewImageBinding
import com.constructionmitra.user.databinding.ProgressBarBinding
import com.constructionmitra.user.ui.profile.ProfileViewModel
import com.constructionmitra.user.utilities.showToast
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class PreviewImageFragment : Fragment() {

    private var saveWhere: String? = null
    private lateinit var binding: FragmentPreviewImageBinding
    private var filePath: String? = null
    private lateinit var progressBarBinding: ProgressBarBinding
    @Inject
    lateinit var appPreferences: AppPreferences

    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            filePath = it.getString(FILE_PATH)
            saveWhere = it.getString(SAVE_WHERE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPreviewImageBinding.inflate(inflater, container, false).apply {
            progressBarBinding = ProgressBarBinding.bind(root)
            Glide.with(root).load(File(filePath)).into(ivPreview)
            tvSave.setOnClickListener {
                // Upload image
                saveImage()
            }

        }
        return binding.root

    }

    private fun saveImage() {
        saveWhere?.let {
            where ->
            showProgress(true)
            when(where){
                IN_WORK_CATALOGUE ->
                    profileViewModel.addWork(
                        appPreferences.getUserId()!!,
                        File(filePath)
                    )
                PROFILE_IMAGE ->
                    profileViewModel.updateProfilePicture(
                        appPreferences.getUserId()!!,
                        appPreferences.getToken()!!,
                        File(filePath)
                    )
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileViewModel.workSampleAdded.observe(viewLifecycleOwner){
            showProgress(false)
            if(it){
               showMessageAndFinishActivity(getString(R.string.work_sample_added))
            }
            else{
                binding.root.showToast(getString(R.string.something_went_wrong))
            }
        }

        profileViewModel.profilePictureUpdated.observe(viewLifecycleOwner){
            showProgress(false)
            if(it){
                showMessageAndFinishActivity(getString(R.string.profile_picture_updated))
            }
            else{
                binding.root.showToast(getString(R.string.something_went_wrong))
            }
        }
    }

    private fun showMessageAndFinishActivity(message: String){
        binding.root.showToast(message)
        requireActivity().setResult(AppCompatActivity.RESULT_OK)
        requireActivity().finish()
    }

    private fun showProgress(show: Boolean) {
        progressBarBinding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }


    companion object {
        private const val  FILE_PATH = "file_path"
        const val  SAVE_WHERE = "save_where"
        const val  PROFILE_IMAGE = "profile_image"
        const val  IN_WORK_CATALOGUE = "work_category"
        const val  IN_COMPANY_DOCUMENTS = "company_docs"



        @JvmStatic
        fun newInstance(filePath: String, saveWhere: String) =
            PreviewImageFragment().apply {
                arguments = Bundle().apply {
                    putString(FILE_PATH, filePath)
                    putString(SAVE_WHERE, saveWhere)
                }
            }
    }
}