package com.constructionmitra.user.ui.employer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.constructionmitra.user.FragmentContainerActivity
import com.constructionmitra.user.R
import com.constructionmitra.user.data.AppPreferences
import com.constructionmitra.user.data.Job
import com.constructionmitra.user.data.JobPostRequest
import com.constructionmitra.user.databinding.FragmentReviewJobEmployerBinding
import com.constructionmitra.user.databinding.ProgressBarBinding
import com.constructionmitra.user.ui.contractor.viewmodels.JobPostViewModel
import com.constructionmitra.user.ui.work.WorkDetailViewModel
import com.constructionmitra.user.utilities.TimeUtils
import com.constructionmitra.user.utilities.constants.Role
import com.constructionmitra.user.utilities.ext.app
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ReviewJobFragment : Fragment() {
    private var job: Job? = null
    private lateinit var binding: FragmentReviewJobEmployerBinding
    private lateinit var progressBarBinding: ProgressBarBinding
    private val appDataConfig by lazy { app().appConfigData }
    private val args: ReviewJobFragmentArgs by navArgs()

    @Inject
    lateinit var appPreferences: AppPreferences
    private val viewModel: WorkDetailViewModel by viewModels()

    private val jobPostViewModel: JobPostViewModel by lazy {
        ViewModelProvider(
            requireActivity()
        )[JobPostViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            job = it.getParcelable(FragmentContainerActivity.PARCELABLE_KEY)
        }
    }

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().popBackStack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReviewJobEmployerBinding.inflate(inflater, container, false).apply {
            progressBarBinding = ProgressBarBinding.bind(root)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // If job is null means navigation done here from Contractor Job Post
        jobPostViewModel.onFragmentSelected(2)
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            backPressedCallback
        )
        setUpUi(args.role, jobPostViewModel.jobPostRequest)
        registerObservers()
    }

    private fun registerObservers() {
    }

    private fun setUpUi(role: Role, jobPostRequest: JobPostRequest){
        val jobRoleDetails = jobPostRequest.jobRoleDetails!!
        val employeeDetails = jobPostRequest.employeeDetails!!
        with(binding){
            when(role){
                Role.ENGINEER_SUPERVISOR -> {
                    viewCriteria.root.visibility = View.GONE
                    viewRequirements.root.visibility = View.GONE
                    viewProjectType.root.visibility = View.GONE
                    val qualification = appDataConfig?.getQualification(jobRoleDetails.qualificationId)
                    // set work type and details
                    viewJobRole.apply {
                        tvTitle.text = getString(R.string.job_role_cat)
                        tvDesc.text = jobPostViewModel.getJobRole(jobRoleDetails.jobWorkId!!)
                    }
                    viewClassification.apply {
                        tvTitle.text = getString(R.string.job_classification_cat)
                        tvDesc.text = jobRoleDetails.classification
                    }
                    viewQualification.apply {
                        tvTitle.text = getString(R.string.qualification_cat)
                        tvDesc.text = qualification?.qualification ?: ""
                    }
                    viewGender.apply {
                        tvTitle.text = getString(R.string.gender_cat)
                        tvDesc.text = jobRoleDetails.gender
                    }
                    viewMinExp.apply {
                        tvTitle.text = getString(R.string.min_experience_cat)
                        tvDesc.text = appDataConfig?.getExperience(jobRoleDetails.minExpId)?.experience ?: ""
                    }
                    viewSalaryRange.apply {
                        tvTitle.text = getString(R.string.salary_cat)
                        tvDesc.text = jobRoleDetails.salaryRange.text
                    }
                    viewJobDesc.apply {
                        tvTitle.text = getString(R.string.job_desc_cat)
                        tvDesc.text = jobRoleDetails.workDesc
                    }
                    viewJobLocation.apply {
                        tvTitle.text = getString(R.string.job_location_cat)
                        tvDesc.text = employeeDetails.projectLocation
                    }
                }

                Role.SPECIALISED_AGENCY -> {
                    viewClassification.root.visibility = View.GONE
                    viewQualification.root.visibility = View.GONE
                    viewGender.root.visibility = View.GONE
                    viewSalaryRange.root.visibility = View.GONE

                    viewJobRole.apply {
                        tvTitle.text = getString(R.string.work_cat)
                        tvDesc.text = jobPostViewModel.getJobRole(jobRoleDetails.jobWorkId!!)
                    }
                    viewCriteria.apply {
                        tvTitle.text = getString(R.string.criteria_cat)
                        tvDesc.text = jobRoleDetails.classification
                    }
                    viewMinExp.apply {
                        tvTitle.text = getString(R.string.min_experience_cat)
                        tvDesc.text = appDataConfig?.getExperience(jobRoleDetails.minExpId)?.experience ?: ""
                    }
                    viewRequirements.apply {
                        tvTitle.text = getString(R.string.requirement_cat)
                        tvDesc.text = jobRoleDetails.requiredDays.plus(" Days")
                    }
                    viewProjectType.apply {
                        tvTitle.text = getString(R.string.project_type_cat)
                        tvDesc.text = appDataConfig?.getProject(jobRoleDetails.projectId!!)?.projectTypeName ?: ""
                    }
                    viewJobDesc.apply {
                        tvTitle.text = getString(R.string.work_description_cat)
                        tvDesc.text = jobRoleDetails.workDesc
                    }
                    viewJobLocation.apply {
                        tvTitle.text = getString(R.string.project_location_cat)
                        tvDesc.text = employeeDetails.projectLocation
                    }
                }
                Role.PETTY_CONTRACTOR -> {
                    viewClassification.root.visibility = View.GONE
                    viewQualification.root.visibility = View.GONE
                    viewGender.root.visibility = View.GONE
                    viewMinExp.root.visibility = View.GONE
                    viewCriteria.root.visibility = View.GONE

                    viewJobRole.apply {
                        tvTitle.text = getString(R.string.job_role_cat)
                        tvDesc.text = jobPostRequest.selectedWorkListString
                    }
                    viewRequirements.apply {
                        tvTitle.text = getString(R.string.requirement_cat)
                        tvDesc.text = jobRoleDetails.requiredDays.plus(" Days")
                    }
                    viewProjectType.apply {
                        tvTitle.text = getString(R.string.project_type_cat)
                        tvDesc.text = appDataConfig?.getProject(jobRoleDetails.projectId!!)?.projectTypeName ?: ""
                    }
                    viewJobDesc.apply {
                        tvTitle.text = getString(R.string.work_description_cat)
                        tvDesc.text = jobRoleDetails.workDesc
                    }
                    viewJobLocation.apply {
                        tvTitle.text = getString(R.string.project_location_cat)
                        tvDesc.text = employeeDetails.projectLocation
                    }
                }
            }

            viewCompanyName.apply {
                tvTitle.text = getString(R.string.company_name_cat)
                tvDesc.text = employeeDetails.companyName
            }
            viewPersonDesignation.apply {
                tvTitle.text = getString(R.string.person_and_designation_cat)
                tvDesc.text = employeeDetails.designation
            }
            viewContactDetails.apply {
                tvTitle.text = getString(R.string.contact_person_cat)
                tvDesc.text = employeeDetails.mobileNumber
            }
            viewJobCreatedOn.apply {
                tvTitle.text = getString(R.string.job_created_on_cat)
                tvDesc.text = TimeUtils.formattedTime2
            }
        }
    }

    private fun showProgress(show: Boolean) {
        progressBarBinding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            ReviewJobFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}