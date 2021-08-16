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
import com.constructionmitra.user.FragmentContainerActivity
import com.constructionmitra.user.R
import com.constructionmitra.user.adapters.JobRoleAdapter
import com.constructionmitra.user.data.AppPreferences
import com.constructionmitra.user.data.ProfileData
import com.constructionmitra.user.databinding.FragmentProfileBinding
import com.constructionmitra.user.databinding.ItemProfileCardBinding
import com.constructionmitra.user.databinding.ProgressBarBinding
import com.constructionmitra.user.utilities.StringUtils
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var profileViewBinding: ItemProfileCardBinding
    private lateinit var progressBarBinding: ProgressBarBinding

    @Inject
    lateinit var appPreferences: AppPreferences

    private val viewModel: ProfileViewModel by viewModels()

    private val startActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                showProgress(true)
                viewModel.fetchProfileInfo(appPreferences.getUserId()!!, appPreferences.getToken()!!)
                Timber.d("startActivityForResult called !")
                result.data?.let {
//                    if (it.getIntExtra(KEY_REQ_CODE, 0) == REQ_CODE_PAYMENT_SUCCESS) {
//                        // refresh content
//                        log("startActivityForResult called !")
//                        showProgress(true)
//                        binding.rvExclusiveVideos.visibility = View.GONE
//                        viewModel.getExclusiveVideos()
//                    }
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
        binding = FragmentProfileBinding.inflate(inflater, container, false).apply {
            progressBarBinding = ProgressBarBinding.bind(root)
            profileViewBinding = ItemProfileCardBinding.bind(viewContainer)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgress(true)
        viewModel.fetchProfileInfo(appPreferences.getUserId()!!, appPreferences.getToken()!!)
        viewModel.profileData.observe(viewLifecycleOwner){
            binding.viewContainer.visibility = View.VISIBLE
            showProgress(false)
            showProgress(false)
            it?.let {
                bindData(it)
            }
        }
    }

    private fun bindData(profileData: ProfileData) {
        with(profileData){
            profileViewBinding.tvName.text = fullName
            if(profileData.jobRoles.isNotEmpty()) {
                profileViewBinding.rvJobRoles.adapter =
                    JobRoleAdapter(profileData.jobRoles, noOfWorker) {}
            }
            else
                profileViewBinding.viewRoles.visibility = View.GONE

            // set phone number
            profileViewBinding.tvPhoneNum.text = profileData.phoneNumber

            // set initial data
            with(binding.viewWorkExp){
                tvHeading.text = getString(R.string.work_exp)
                tvDetail.text = if(!profileData.experience.isNullOrEmpty()) profileData.experience else
                    getString(R.string.work_exp_in_years)
                tvCta.text = getString(R.string.share_your_exp)
                ivIcon.setImageResource(R.drawable.ic_bag)
                tvCta.setOnClickListener {
                    navigateTo(WorkExpFragment::class.java.name)
                }
            }

            with(binding.workLocationPriority){
                tvHeading.text = getString(R.string.location_priority)
                tvDetail.text = if (!profileData.prefferedLocations.isNullOrEmpty())
                    StringUtils.stringLocations(profileData.prefferedLocations)
                else
                    getString(R.string.which_location_you_want_to_work)
                tvCta.text = getString(R.string.select_city_hn)
                ivIcon.setImageResource(R.drawable.ic_location)
                tvCta.setOnClickListener {
                    navigateTo(WorkLocationFragment::class.java.name)
                }
            }

            with(binding.viewSelectedWork){
                tvHeading.text = getString(R.string.selected_work)
                tvDetail.text = if (!profileData.jobRoles.isNullOrEmpty())
                StringUtils.jobRolesInString(profileData.jobRoles)
                else
                    "आप कोसा काम करना चाहते हैं"
                tvCta.text = getString(R.string.change_selected_work)
                ivIcon.setImageResource(R.drawable.ic_bag)
            }

            with(binding.viewUploadId){
                tvHeading.text = getString(R.string.contractor_verification_doc)
                tvDetail.text = "ID"
                tvCta.text = getString(R.string.upload_doc)
                ivIcon.visibility = View.GONE
            }

        }
    }

    private fun navigateTo(fragmentName: String){
        startActivityForResult.launch(
            Intent(requireContext(), FragmentContainerActivity::class.java).apply {
                putExtra(FragmentContainerActivity.FRAGMENT_NAME, fragmentName)
            }
        )
        requireActivity().overridePendingTransition(
            R.anim.enter_anim_activity,
            R.anim.exit_anim_activity
        )
    }

    private fun showProgress(show: Boolean) {
        progressBarBinding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
        binding.viewContainer.visibility = if (show) View.INVISIBLE else View.VISIBLE
    }


    companion object {

        @JvmStatic
        fun newInstance() =
            ProfileFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}