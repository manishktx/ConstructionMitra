package com.constructionmitra.user.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.RadioButton
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.constructionmitra.user.R
import com.constructionmitra.user.api.ProfileRequests
import com.constructionmitra.user.data.AboutData
import com.constructionmitra.user.data.AppPreferences
import com.constructionmitra.user.data.ProfileData
import com.constructionmitra.user.databinding.FragmentAboutBinding
import com.constructionmitra.user.databinding.ProgressBarBinding
import com.constructionmitra.user.utilities.AppUtils
import com.constructionmitra.user.utilities.constants.AppConstants
import com.constructionmitra.user.utilities.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_request_for_work.*
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class AboutFragment : Fragment() {

    private lateinit var binding: FragmentAboutBinding
    private lateinit var progressBarBinding: ProgressBarBinding
    @Inject lateinit var appPreferences: AppPreferences
    @Inject lateinit var profileRequests: ProfileRequests

    private var isProfileUpdated = false

    private val viewModel: ProfileViewModel by viewModels()

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if(isProfileUpdated)
                requireActivity().setResult(AppCompatActivity.RESULT_OK)
            requireActivity().finish()
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
            val adapter = ArrayAdapter(requireContext(), R.layout.item_drop_down_center, AppConstants.ages)
            (textAge.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgress(true)
        viewModel.fetchProfileInfo(appPreferences.getUserId()!!, appPreferences.getToken()!!)
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
                    userId = appPreferences.getUserId()!!,
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
                }
            }
        }
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.rbMale ->
                    if (checked) {
                        // Pirates are the best
                    }
                R.id.rbFemale ->
                    if (checked) {
                        // Ninjas rule
                    }

                R.id.rbOther ->
                    if (checked) {
                        // Ninjas rule
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
            if(it.toString().trim().length < 10){
                binding.root.showToast(getString(R.string.enter_valid_mobile_num))
                return@getAboutData null
            }
            aboutData.phoneNum = it.toString()
        }

        if(binding.etName.text!!.isEmpty()){
            binding.root.showToast(getString(R.string.enter_valid_name))
            return null
        }


        with(binding){
            // set gender
            aboutData.gender = if(rbFemale.isChecked || rbMale.isChecked || rbOther.isChecked){
                    AppUtils.genderInEnglish(
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
            binding.profileData = profileData
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