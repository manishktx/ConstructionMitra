package com.constructionmitra.user.ui.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.RadioButton
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.constructionmitra.user.R
import com.constructionmitra.user.api.ProfileRequests
import com.constructionmitra.user.data.AboutData
import com.constructionmitra.user.data.AppPreferences
import com.constructionmitra.user.data.ProfileData
import com.constructionmitra.user.databinding.FragmentAboutBinding
import com.constructionmitra.user.databinding.ProgressBarBinding
import com.constructionmitra.user.ui.PreviewImageActivity
import com.constructionmitra.user.ui.PreviewImageFragment
import com.constructionmitra.user.utilities.AppUtils
import com.constructionmitra.user.utilities.BitmapConfig
import com.constructionmitra.user.utilities.CMBitmapConfig
import com.constructionmitra.user.utilities.constants.AppConstants
import com.constructionmitra.user.utilities.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_request_for_work.*
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class AboutFragment : Fragment() {

    private lateinit var binding: FragmentAboutBinding
    private lateinit var progressBarBinding: ProgressBarBinding
    @Inject lateinit var appPreferences: AppPreferences
    @Inject lateinit var profileRequests: ProfileRequests

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
        binding = FragmentAboutBinding.inflate(inflater, container, false).apply {
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
                bindData(it)
            }
        }

        // set listener
        binding.tvSaveDetails.setOnClickListener {
            getAboutData()?.let {
                Timber.d("About data = $it")
                showProgress(true)
                viewModel.updateProfile(profileRequests.updateAbout(
                    userId = appPreferences.getUserId(),
                    token = appPreferences.getToken()!!,
                    aboutData = it
                ))
            }
        }

        binding.ivAvatar.setOnClickListener {
            pickImageFromGallery()
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

        viewModel.galleryImageSaved.observe(viewLifecycleOwner){
            it?.let {
                navigateToPreview(it)
            }
        }
    }

    private fun pickImageFromGallery() {
        pickImage.launch("image/*")
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

    private fun getAboutData(): AboutData?{
        // check validation for mobile and name
        val aboutData = AboutData()
        binding.etPhoneNum.text?.let {
            if(it.toString().trim().length < 10){
                binding.root.showToast(getString(R.string.enter_valid_mobile_num))
                return@getAboutData null
            }
            aboutData.phoneNum = it.toString()
        }

        binding.etOtherPhoneNum.text?.let {
            if(it.toString().trim().length == 10){
               aboutData.phoneNum = it.toString()
            }
        }

        if(binding.etName.text!!.isEmpty()){
            binding.root.showToast(getString(R.string.enter_valid_name))
            return null
        }


        with(binding){
            // set gender
            aboutData.gender = if(rbFemale.isChecked || rbMale.isChecked || rbOther.isChecked){
                    AppUtils.genderType(
                        (root.findViewById(rgGender.checkedRadioButtonId) as RadioButton).text.toString()
                    )
            } else ""

            // set age
            textAge.editText?.let {
                it.takeIf {  it.text.toString() != getString(R.string.hint_your_age)}?.let {
                    _edit ->
                    aboutData.age = _edit.text.toString()
                }
            }
            etHomeAddress.text?.let {
                aboutData.homeAddress  = it.toString()
            }
            etCurrentLocation.text?.let {
                aboutData.currentAddress  = it.toString()
            }
        }

        return aboutData.apply {
            name = binding.etName.text.toString()
            phoneNum = binding.etPhoneNum.text.toString()
            binding.etOtherPhoneNum.text?.let {
                 otherMobileNum = it.toString()
            }
        }
    }

    private fun bindData(profileData: ProfileData) {
        with(binding){
            this.profileData = profileData
            if(profileData.age.isNotEmpty())
                (textAge.editText as AutoCompleteTextView).setText(profileData.age)

            val adapter = ArrayAdapter(requireContext(), R.layout.item_drop_down, AppConstants.ages)
            (textAge.editText as? AutoCompleteTextView)?.setAdapter(adapter)
            if(profileData.gender.isNotEmpty())
            {
                this.gender = AppUtils.genderTypeValue(profileData.gender)
            }
        }
    }

    private fun showProgress(show: Boolean) {
        progressBarBinding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            AboutFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}