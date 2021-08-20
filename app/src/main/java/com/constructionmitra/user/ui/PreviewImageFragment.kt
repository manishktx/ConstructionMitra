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
                showProgress(true)
                profileViewModel.addWork(
                    appPreferences.getUserId()!!,
                    File(filePath)
                )
            }

        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileViewModel.workSampleAdded.observe(viewLifecycleOwner){
            showProgress(false)
            if(it){
                binding.root.showToast(getString(R.string.work_sample_added))
                requireActivity().setResult(AppCompatActivity.RESULT_OK)
                requireActivity().finish()
            }
            else{
                binding.root.showToast(getString(R.string.something_went_wrong))
            }
        }
    }

    private fun showProgress(show: Boolean) {
        progressBarBinding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }


    companion object {
        private const val  FILE_PATH = "file_path"
        @JvmStatic
        fun newInstance(filePath: String) =
            PreviewImageFragment().apply {
                arguments = Bundle().apply {
                    putString(FILE_PATH, filePath)
                }
            }
    }
}