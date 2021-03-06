package com.constructionmitra.user.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.constructionmitra.user.*
import com.constructionmitra.user.data.AppPreferences
import com.constructionmitra.user.databinding.FragmentChooseYourWorkSubCategoriesBinding
import com.constructionmitra.user.databinding.ProgressBarBinding
import com.constructionmitra.user.ui.dialogs.GetAgencyDetailsDialog
import com.constructionmitra.user.ui.dialogs.GetFirmDetailsDialog
import com.constructionmitra.user.ui.employer.engineer_supervisor.EngineerMainActivity
import com.constructionmitra.user.ui.login.adapters.WorkSubCategoryAdapter
import com.constructionmitra.user.utilities.constants.AppConstants
import com.constructionmitra.user.utilities.constants.IntentConstants
import com.constructionmitra.user.utilities.constants.UserType
import com.constructionmitra.user.utilities.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class WorkSubCategoriesFragment : Fragment() {

    private var isFromHome: Boolean = false
    private lateinit var binding: FragmentChooseYourWorkSubCategoriesBinding
    private lateinit var progressBarBinding: ProgressBarBinding

    private val viewModel: LoginViewModel by viewModels()

    private val args: WorkSubCategoriesFragmentArgs by navArgs()
    @Inject
    lateinit var appPreferences: AppPreferences

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
        binding = FragmentChooseYourWorkSubCategoriesBinding.inflate(inflater, container, false).apply {
            progressBarBinding = ProgressBarBinding.bind(root)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgress(true)
        // Check if navigation come from [@HomeFragment] if yes then update UI
        checkIfCameFromHome()
        viewModel.requestJobRoles(args.jobCategory)

        // set listener on next button/ Save button
        binding.tvNext.setOnClickListener {
            viewModel.jobRoles.value?.filter {
                it.isChecked
            }?.takeIf {
                !it.isNullOrEmpty()
            }?.let {
                Timber.d("checked ids are = ${viewModel.getCheckedItemsIds(it)}")

                showProgress(true)
                viewModel.updateJobRoles(
                    appPreferences.getUserId()!!,
                    appPreferences.getToken()!!,
                    viewModel.getCheckedItemsIds(it)
                )
            } ?: run {
                binding.root.showToast("Please Choose AtLeast 1 Job Role")
            }
        }
        registerObservers()
    }

    private fun checkIfCameFromHome() {
        arguments?.getString(FragmentContainerActivity.PATH)?.let {
            it.takeIf { it == IntentConstants.PATH_HOME }?.let {
                _path ->
                // change UI
                isFromHome = true
                binding.tvNext.text = getString(R.string.save)
            }
        }
    }

    private fun registerObservers() {
        viewModel.jobRoles.observe(viewLifecycleOwner) { jobRoles ->
            showProgress(false)
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO){
                appPreferences.profile?.let {  profileData ->
                    jobRoles.apply {
                        for(jobRole in this){
                            jobRole.isChecked = profileData.jobRoles.contains(jobRole)
                        }
                    }
                }
                withContext(Dispatchers.Main){
                    binding.rvSubCategories.adapter =
                        WorkSubCategoryAdapter(jobRoles, isSubCategory = true) {

                        }
                }
            }
        }

        viewModel.updateJobRoles.observe(viewLifecycleOwner){
            showProgress(false)
            if(isFromHome){
                requireActivity().setResult(AppCompatActivity.RESULT_OK)
                requireActivity().finish()
            }
            else {
                when(appPreferences.userType()){
                    UserType.SPECIALISED_AGENCY -> showAgencyDetailsDialog()
                    UserType.PETTY_CONTRACTOR -> showGetFirmDetailsDialog()
                    UserType.WORKER -> navigateToHome()
                    UserType.ENGINEER_SUPERVISOR -> navigateToEngineer()
                }

            }
        }

        viewModel.updateFirmDetails.observe(viewLifecycleOwner){
            showProgress(false)
            navigateToHome()
        }

        viewModel.errorMsg.observe(viewLifecycleOwner){
            showProgress(false)
            it?.let {
                binding.root.showToast(it)
            }
        }
    }

    private fun navigateToEngineer(){
        appPreferences.saveUserType(AppConstants.USER_TYPE_ENGINEER)
        Intent(context, EngineerEmpActivity::class.java).apply {
            requireContext().startActivity(this)
        }
        requireActivity().finish()
        requireActivity().overridePendingTransition(R.anim.enter_anim_activity, R.anim.exit_anim_activity)
    }
    private fun navigateToHome(){
        appPreferences.saveUserType(AppConstants.USER_TYPE_PETTY_CONTRACTOR)

        //Comment By Rattan
        /*Intent(context, MainActivity::class.java).apply {
            requireContext().startActivity(this)
        }*/
        //Edit By Rattan
        Intent(context, EngineerMainActivity::class.java).apply {
            requireContext().startActivity(this)
        }
        requireActivity().finish()
        requireActivity().overridePendingTransition(R.anim.enter_anim_activity, R.anim.exit_anim_activity)
    }

    private fun showGetFirmDetailsDialog(){
        GetFirmDetailsDialog.newInstance {
                firmName, numOfWorkers ->
            // Update firm details
            showProgress(true)
            viewModel.updateFirmDetails(
                appPreferences.getUserId()!!, firmName, numOfWorkers, "TnNEOEQ1OXFvYXJRdkZyUWx6SWVlZz09"
            )
//                            navigateToHome()
        }.show(childFragmentManager, "alert_dialog")
    }

    private fun showAgencyDetailsDialog(){
        GetAgencyDetailsDialog.newInstance {
                firmName, desc ->
            // Update firm details
            showProgress(true)
            viewModel.updateFirmDetails(
                appPreferences.getUserId(), firmName, desc, "TnNEOEQ1OXFvYXJRdkZyUWx6SWVlZz09"
            )
//                            navigateToHome()
        }.show(childFragmentManager, "alert_dialog")
    }

    private fun showProgress(show: Boolean) {
        progressBarBinding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }


    companion object {

        @JvmStatic
        fun newInstance() =
            WorkSubCategoriesFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}