package com.constructionmitra.user.ui.contractor

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
import com.constructionmitra.user.api.ProfileRequests
import com.constructionmitra.user.data.AppPreferences
import com.constructionmitra.user.data.ContractorAboutData
import com.constructionmitra.user.data.ProfileData
import com.constructionmitra.user.databinding.FragmentContractorProfileBinding
import com.constructionmitra.user.databinding.ProgressBarBinding
import com.constructionmitra.user.ui.PreviewImageActivity
import com.constructionmitra.user.ui.PreviewImageFragment
import com.constructionmitra.user.ui.contractor.viewmodels.ContractorProfileViewModel
import com.constructionmitra.user.utilities.BindingAdapters
import com.constructionmitra.user.utilities.BitmapConfig
import com.constructionmitra.user.utilities.CMBitmapConfig
import com.constructionmitra.user.utilities.showToast
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.io.File
import java.lang.Exception
import javax.inject.Inject

@AndroidEntryPoint
class ContractorProfileFragment : Fragment() {

    private var _binding: FragmentContractorProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var progressBarBinding: ProgressBarBinding

    private val viewModel: ContractorProfileViewModel by viewModels()
    @Inject
    lateinit var appPreferences: AppPreferences
    @Inject lateinit var profileRequests: ProfileRequests

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContractorProfileBinding.inflate(inflater, container, false).apply {
            progressBarBinding = ProgressBarBinding.bind(root)
            tvSave.setOnClickListener {
                if(areDetailsValidToUpdateProfile()){
                    saveProfile()
                }
            }
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            showProgress(true)
            viewModel.fetchProfileInfo(
                appPreferences.getUserId(),
                appPreferences.getToken()!!
            )
            viewUploadDocument.setOnClickListener {
                pickImage.launch("image/*")
            }
        }
        registerObservers()
    }

    private fun registerObservers(){
        viewModel.profileData.observe(viewLifecycleOwner){
            showProgress(false)
            it?.let {
                bindData(it)
            }
        }

        viewModel.galleryImageSaved.observe(viewLifecycleOwner){
            navigateToPreview(it)
        }

        viewModel.profileUpdated.observe(viewLifecycleOwner){
            binding.root.showToast("Profile Updated")
        }

        onError()
    }

    private fun bindData(profileData: ProfileData) {
        with(binding){
            etEmployeeName.setText(profileData.fullName)
            etMobileNum.setText(profileData.phoneNumber)
            profileData.userDoc?.takeIf { !it.isNullOrEmpty() }?.let {
                ivVerificationDocument.visibility = View.VISIBLE
                BindingAdapters.loadResizedImage(ivVerificationDocument, it)
            }
        }
    }

    private fun saveProfile() {
        showProgress(true)
        viewModel.updateProfile(
            profileRequests.updateContractorProfile(
                appPreferences.getUserId(),
                appPreferences.getToken()!!,
                getProfileData())
        )
    }

    private fun getProfileData(): ContractorAboutData{
        if(!areDetailsValidToUpdateProfile())
            throw Exception("Details are not valid to retrieve profile update data")
        with(binding){
           return ContractorAboutData(
                employeeName = etEmployeeName.text.toString(),
                designation= etDesignation.text.toString(),
                emailId = etEmail.text.toString(),
                mobileNumber = etMobileNum.text.toString(),
                companyName = etCompany.text.toString(),
                companyAddress = etAddress.text.toString(),
                city = etSelectCity.text.toString(),
            )
        }
    }
    private fun navigateToPreview(it: File) {
        startActivityForResult.launch(
            Intent(requireContext(), PreviewImageActivity::class.java).apply {
                putExtra(PreviewImageActivity.FILE_PATH, it.absolutePath)
                putExtra(PreviewImageActivity.SAVE_WHERE, PreviewImageFragment.IN_COMPANY_DOCUMENTS)
            }
        )
        requireActivity().overridePendingTransition(
            R.anim.enter_anim_activity,
            R.anim.exit_anim_activity
        )
    }

    private fun areDetailsValidToUpdateProfile(): Boolean{
        binding.etMobileNum.text?.let {
            if(it.toString().trim().length < 10){
                binding.root.showToast(getString(R.string.enter_valid_mobile_num))
                return false
            }
        }

        binding.etEmployeeName.text?.let {
            if(it.toString().trim().isEmpty()){
                binding.root.showToast("Please enter a valid name")
            }
        }
        return true
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}