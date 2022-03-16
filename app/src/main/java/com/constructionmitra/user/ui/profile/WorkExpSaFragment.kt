package com.constructionmitra.user.ui.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.constructionmitra.user.R
import com.constructionmitra.user.api.ProfileRequests
import com.constructionmitra.user.data.AppPreferences
import com.constructionmitra.user.databinding.FragmentAboutWorkExpSaBinding
import com.constructionmitra.user.databinding.ProgressBarBinding
import com.constructionmitra.user.ui.PreviewImageActivity
import com.constructionmitra.user.ui.PreviewImageFragment
import com.constructionmitra.user.utilities.BindingAdapters
import com.constructionmitra.user.utilities.BitmapConfig
import com.constructionmitra.user.utilities.CMBitmapConfig
import com.constructionmitra.user.utilities.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_about_work_exp.*
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class WorkExpSaFragment : Fragment() {

    private var isUpdated: Boolean = false
    private var adapter: WorkExpAdapter? = null
    private lateinit var binding: FragmentAboutWorkExpSaBinding
    private lateinit var progressBarBinding: ProgressBarBinding

    private val viewModel: ProfileViewModel by viewModels()

    @Inject lateinit var appPreferences: AppPreferences
    @Inject lateinit var profileRequests: ProfileRequests

    private val bitmapConfig: BitmapConfig by lazy {
        CMBitmapConfig()
    }

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if(isUpdated)
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
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAboutWorkExpSaBinding.inflate(inflater, container, false).apply {
            progressBarBinding = ProgressBarBinding.bind(root)
            tvSave.setOnClickListener {
                adapter?.let {
                    if(it.currentSelection >= 0){
                        // Update work exp
                        showProgress(true)
                        viewModel.updateProfile(profileRequests.updateExp(
                            userId = appPreferences.getUserId()!!,
                            token = appPreferences.getToken()!!,
                            exp = viewModel.workExpOptions.value?.get(it.currentSelection)!!
                        ))
                    }
                }
            }

            icCamera.setOnClickListener{
                pickImageFromGallery()
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgress(true)
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            backPressedCallback
        )
        viewModel.getWorkExpOptions()
        viewModel.workExpOptions.observe(viewLifecycleOwner){
            showProgress(false)
            binding.rvWorkExp.adapter = WorkExpAdapter(it){

            }.apply {
                adapter = this
            }
        }
        registerObserver()
    }

    private fun registerObserver(){
        viewModel.profileUpdated.observe(viewLifecycleOwner){
            showProgress(false)
            if(it){
                isUpdated = true
                binding.root.showToast("Your Work Exp Updated!")
            }
        }

        viewModel.profileData.observe(viewLifecycleOwner){
            showProgress(false)
            it?.let { profileData ->
                profileData.userDoc.takeIf { it.isNotEmpty() } ?.let { userDock ->
                    BindingAdapters.loadImage(icCamera, userDock)
                }
            }
        }

        viewModel.galleryImageSaved.observe(viewLifecycleOwner){
            showProgress(false)
            it?.let {
                navigateToPreview(it)
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

    private fun showProgress(show: Boolean) {
        progressBarBinding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun pickImageFromGallery() {
        pickImage.launch("image/*")
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            WorkExpFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
