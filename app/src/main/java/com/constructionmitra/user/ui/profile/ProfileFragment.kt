package com.constructionmitra.user.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.constructionmitra.user.R
import com.constructionmitra.user.adapters.JobRoleAdapter
import com.constructionmitra.user.data.AppPreferences
import com.constructionmitra.user.data.ProfileData
import com.constructionmitra.user.databinding.FragmentProfileBinding
import com.constructionmitra.user.databinding.ItemProfileCardBinding
import com.constructionmitra.user.databinding.ProgressBarBinding
import com.constructionmitra.user.utilities.StringUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var profileViewBinding: ItemProfileCardBinding
    private lateinit var progressBarBinding: ProgressBarBinding

    @Inject
    lateinit var appPreferences: AppPreferences

    private val viewModel: ProfileViewModel by viewModels()

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
            profileViewBinding = ItemProfileCardBinding.bind(root)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchProfileInfo(appPreferences.getUserId()!!, appPreferences.getToken()!!)
        viewModel.profileData.observe(viewLifecycleOwner){
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

            // setLocation
            binding.viewPreferredWorkLocation.tvProfileSectionTitle.text =
                getString(R.string.preferred_location)
            val isPreferredLocationsAvailable = profileData.prefferedLocations.isNotEmpty()
            binding.viewPreferredWorkLocation.tvDetail.text = if(isPreferredLocationsAvailable)
                StringUtils.stringPresentationOfLocations(profileData.prefferedLocations) else
                    getString(R.string.preferred_location_for_work)

            binding.viewWorkPriority.profileCardContainer.visibility = View.GONE

        }
    }

    private fun showProgress(show: Boolean) {
        progressBarBinding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
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