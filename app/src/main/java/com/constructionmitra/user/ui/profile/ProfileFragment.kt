package com.constructionmitra.user.ui.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.constructionmitra.user.FragmentContainerActivity
import com.constructionmitra.user.R
import com.constructionmitra.user.adapters.CatalogPreviewAdapter
import com.constructionmitra.user.data.AppPreferences
import com.constructionmitra.user.data.ProfileData
import com.constructionmitra.user.data.WorkHistory
import com.constructionmitra.user.databinding.FragmentProfileBinding
import com.constructionmitra.user.databinding.ItemProfileCard3Binding
import com.constructionmitra.user.databinding.ProgressBarBinding
import com.constructionmitra.user.ui.ShowImageFragment
import com.constructionmitra.user.ui.login.WorkSubCategoriesFragment
import com.constructionmitra.user.utilities.BindingAdapters
import com.constructionmitra.user.utilities.StringUtils
import com.constructionmitra.user.utilities.constants.IntentConstants
import com.constructionmitra.user.utilities.showToast
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
//    private lateinit var profileViewBinding: ItemProfileCard3Binding
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
//            profileViewBinding = ItemProfileCard3Binding.bind(viewContainer)
            viewProfileCard.tvChangeAbout.setOnClickListener {
                navigateTo(AboutFragment::class.java.name)
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgress(true)
        // get profile info
        viewModel.fetchProfileInfo(appPreferences.getUserId()!!, appPreferences.getToken()!!)

        // get work history
        lifecycleScope.launchWhenResumed {
            viewModel.workHistory(appPreferences.getUserId()!!)
        }

        viewModel.profileData.observe(viewLifecycleOwner){
            binding.viewContainer.visibility = View.VISIBLE
            showProgress(false)
            it?.let {
                bindData(it)
            }
        }

        registerObservers()
    }

    private fun registerObservers() {
        viewModel.workHistory.observe(viewLifecycleOwner){
            it?.takeIf { it.isNotEmpty() }?.let {
                with(binding.viewCatalog){
                    textIns.visibility = View.GONE
                    tvCta.text = getString(R.string.change_catalog_images)
                    tvHeading.text = getString(R.string.edit_catalog_heading)
                    rvCatalog.adapter = CatalogPreviewAdapter(
                        it,
                        onItemClick =  {
                            _workHistory ->
                            navigateToShowImage(_workHistory.image)
                        },
                        onViewAllClick = {
                            navigateTo(CatalogFragment::class.java.name)
                        }
                    )
                    rvCatalog.addItemDecoration(getDividerDecorator())
                    // cta
                    tvCta.setOnClickListener {
                        navigateTo(WorkExpFragment::class.java.name)
                    }
                }
            }?: run {
                // Work history is empty
                with(binding.viewCatalog){
                    rvCatalog.visibility = View.GONE
                }
            }
        }

        onError()
    }

    private fun bindData(profileData: ProfileData) {
        with(profileData){
            binding.profileData = profileData
            with(binding.viewProfileCard){
                tvName.text = fullName
                tvFirmName.text  = firmName
                tvMobileNum.text  = phoneNumber
                textGender.text = gender
                textAge.text = getString(R.string.age_formatter, age)
                textHomeTown.text = getString(R.string.home_address_formatter, address)
                textCurrentAddress.text = getString(R.string.current_address_formatter, currentResidence)
                BindingAdapters.profilePic(ivAvatar, profileData.profilePic)
//                this.profileData = profileData
            }
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
                tvCta.setOnClickListener {
                    navigateTo(WorkSubCategoriesFragment::class.java.name)
                }
                ivIcon.setImageResource(R.drawable.ic_bag)
            }

            with(binding.viewUploadId){
                tvHeading.text = getString(R.string.contractor_verification_doc)
                if(profileData.userDoc.isNotEmpty()){
                    viewIdCard.visibility = View.VISIBLE
                    BindingAdapters.loadResizedImage(ivWork, profileData.userDoc)
                    ivWork.setOnClickListener {
                        navigateToShowImage(profileData.userDoc)
                    }
                }
                else{
                    viewIdCard.visibility = View.GONE
                }
                tvCta.text = getString(R.string.upload_doc)
                tvCta.setOnClickListener {
                    navigateTo(CompanyLetterHeadFragment::class.java.name)
                }
            }

        }
    }

    private fun navigateTo(fragmentName: String){
        startActivityForResult.launch(
            Intent(requireContext(), FragmentContainerActivity::class.java).apply {
                putExtra(FragmentContainerActivity.FRAGMENT_NAME, fragmentName)
                putExtra(FragmentContainerActivity.PATH, IntentConstants.PATH_HOME)
            }
        )
        requireActivity().overridePendingTransition(
            R.anim.enter_anim_activity,
            R.anim.exit_anim_activity
        )
    }

    private fun getDividerDecorator(): DividerItemDecoration {
        return DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL).also {
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.divider
            )?.apply {
                it.setDrawable(this)
            }
        }
    }

    private fun navigateToShowImage(imageUrl: String){
        startActivityForResult.launch(
            Intent(requireContext(), FragmentContainerActivity::class.java).apply {
                putExtra(FragmentContainerActivity.FRAGMENT_NAME, ShowImageFragment::class.java.name)
                putExtra(FragmentContainerActivity.IMAGE_URL, imageUrl)
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