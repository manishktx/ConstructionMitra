package com.constructionmitra.user.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.constructionmitra.user.FragmentContainerActivity
import com.constructionmitra.user.R
import com.constructionmitra.user.adapters.*
import com.constructionmitra.user.data.AppPreferences
import com.constructionmitra.user.data.ProfileData
import com.constructionmitra.user.databinding.FragmentHomeBinding
import com.constructionmitra.user.databinding.ItemProfileCardBinding
import com.constructionmitra.user.ui.login.WorkSubCategoriesFragment
import com.constructionmitra.user.ui.profile.*
import com.constructionmitra.user.utilities.BindingAdapters
import com.constructionmitra.user.utilities.constants.IntentConstants
import com.constructionmitra.user.utilities.showSnackBarShort
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var profileViewBinding: ItemProfileCardBinding
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    @Inject lateinit var appPreferences: AppPreferences

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val startActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                Timber.d("in startActivityForResult(), resultCode is RESULT_OK")
                homeViewModel.fetchProfileInfo(appPreferences.getUserId()!!, appPreferences.getToken()!!)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            profileViewBinding = ItemProfileCardBinding.bind(topBar)
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = HomePagerAdapter(this)
        // set tabs
        with(binding){
            TabLayoutMediator(tabs, viewPager) {
                    tab, position ->
//                tab.setIcon(getTabIcon(position))
                tab.text = getTabTitle(position)
            }.attach()
        }

        // setUp profile banner
        binding.vpProfile.adapter = ProfilePagerAdapter(
            PROFILE_CARDS,
        ){
            // onItemClickListener
            when(it.profile){
                Profile.ABOUT ->
                    navigateTo(AboutFragment::class.java.name)
                Profile.EXPERIENCE ->
                    navigateTo(WorkExpFragment::class.java.name)
                Profile.WORK_PRIORITY ->
                    navigateTo(WorkPriorityFragment::class.java.name)
                Profile.WORK_LOCATION ->
                    navigateTo(WorkLocationFragment::class.java.name)
                Profile.PHOTO_AND_ID_CARD ->
                    navigateTo(CompanyLetterHeadFragment::class.java.name)
            }
        }
        binding.dotsIndicator.setViewPager(binding.vpProfile)

        // Update profileView
        setUpProfile()
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

    private fun setUpProfile() {
        profileViewBinding.tvChangeSelectedWork.setOnClickListener {
            navigateTo(WorkSubCategoriesFragment::class.java.name)
        }
        appPreferences.getUserId()?.let {
            userId ->
            // Calling api
            homeViewModel.fetchProfileInfo(userId, appPreferences.getToken()!!)
        }?: binding.root.showSnackBarShort("User not found!")

        // Observe result
        homeViewModel.profileData.observe(viewLifecycleOwner){
            it?.let {
                profileData ->
                // Update UI
                updateUi(profileData)
            }
        }
    }

    private fun updateUi(profileData: ProfileData) {
        with(profileData){
            BindingAdapters.profilePic(profileViewBinding.ivAvatar, profileData.profilePic)
            profileViewBinding.tvName.text = fullName
            profileViewBinding.tvFirmName.text = firmName
            if(profileData.jobRoles.isNotEmpty()) {
                profileViewBinding.rvJobRoles.adapter =
                    JobRoleAdapter(profileData.jobRoles, noOfWorker) {}
                return
            }
            profileViewBinding.viewRoles.visibility = View.GONE
        }
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            MY_PAGE_INDEX -> "Work Available"
            PLANT_LIST_PAGE_INDEX -> "Work Applied"
            else -> null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}