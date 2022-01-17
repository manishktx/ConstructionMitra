package com.constructionmitra.user.ui.employer

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.constructionmitra.user.FragmentContainerActivity
import com.constructionmitra.user.R
import com.constructionmitra.user.data.AppPreferences
import com.constructionmitra.user.data.PostedJob
import com.constructionmitra.user.databinding.FragmentReviewJobEmployerBinding
import com.constructionmitra.user.databinding.ProgressBarBinding
import com.constructionmitra.user.ui.work.WorkDetailViewModel
import com.constructionmitra.user.utilities.constants.Role
import com.constructionmitra.user.utilities.ext.app
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ViewJobDetailsFragment : Fragment() {
    private var job: PostedJob? = null
    private lateinit var binding: FragmentReviewJobEmployerBinding
    private lateinit var progressBarBinding: ProgressBarBinding

    @Inject
    lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            job = it.getParcelable(FragmentContainerActivity.PARCELABLE_POSTED_JOB)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReviewJobEmployerBinding.inflate(inflater, container, false).apply {
            progressBarBinding = ProgressBarBinding.bind(root)
            viewTnC.visibility = View.GONE
            appBar.visibility = View.VISIBLE
            tvTitle.text = HtmlCompat.fromHtml(getString(R.string.app_title_english), HtmlCompat.FROM_HTML_MODE_LEGACY)
            tvTitle.typeface = Typeface.SERIF
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        job?.let {
            setUpUi(EmployerUtil.findRole(it.categoryName), it)
        }
    }


    private fun setUpUi(role: Role, job: PostedJob){
        with(binding){
            when(role){
                Role.ENGINEER_SUPERVISOR -> {
                    viewCriteria.root.visibility = View.GONE
                    viewRequirements.root.visibility = View.GONE
                    viewProjectType.root.visibility = View.GONE
                    // set work type and details
                    viewJobRole.apply {
                        tvTitle.text = getString(R.string.job_role_cat)
                        tvDesc.text = job.jobRole
                    }
                    viewClassification.apply {
                        tvTitle.text = getString(R.string.job_classification_cat)
                        tvDesc.text = job.classification
                    }
                    viewQualification.apply {
                        tvTitle.text = getString(R.string.qualification_cat)
                        tvDesc.text = job.qualification
                    }
                    viewGender.apply {
                        tvTitle.text = getString(R.string.gender_cat)
                        tvDesc.text = job.gender
                    }
                    viewMinExp.apply {
                        tvTitle.text = getString(R.string.min_experience_cat)
                        tvDesc.text = job.experience
                    }
                    viewSalaryRange.apply {
                        tvTitle.text = getString(R.string.salary_cat)
                        tvDesc.text = getString(R.string.salary_formatter, job.minSalary, job.maxSalary)
                    }
                    viewJobDesc.apply {
                        tvTitle.text = getString(R.string.job_desc_cat)
                        tvDesc.text = job.workDesc
                        if(job.workDesc.isNullOrEmpty())
                            tvDesc.text = "Not available"
                    }
                    viewJobLocation.apply {
                        tvTitle.text = getString(R.string.job_location_cat)
                        tvDesc.text = job.locationName
                    }
                }

                Role.SPECIALISED_AGENCY -> {
                    viewClassification.root.visibility = View.GONE
                    viewQualification.root.visibility = View.GONE
                    viewGender.root.visibility = View.GONE
                    viewSalaryRange.root.visibility = View.GONE

                    viewJobRole.apply {
                        tvTitle.text = getString(R.string.work_cat)
                        tvDesc.text = job.jobRole
                    }
                    viewCriteria.apply {
                        tvTitle.text = getString(R.string.criteria_cat)
                        tvDesc.text = job.criteria
                    }
                    viewMinExp.apply {
                        tvTitle.text = getString(R.string.min_experience_cat)
                        tvDesc.text = job.experience
                    }
                    viewRequirements.apply {
                        tvTitle.text = getString(R.string.requirement_cat)
                        tvDesc.text = job.totalDaysRequired.plus(" Days")
                    }
                    viewProjectType.apply {
                        tvTitle.text = getString(R.string.project_type_cat)
                        tvDesc.text = job.projectType
                    }
                    viewJobDesc.apply {
                        tvTitle.text = getString(R.string.work_description_cat)
                        tvDesc.text = job.workDesc
                    }
                    viewJobLocation.apply {
                        tvTitle.text = getString(R.string.project_location_cat)
                        tvDesc.text = job.locationName
                    }
                }
                Role.PETTY_CONTRACTOR -> {
                    viewClassification.root.visibility = View.GONE
                    viewQualification.root.visibility = View.GONE
                    viewGender.root.visibility = View.GONE
                    viewMinExp.root.visibility = View.GONE
                    viewSalaryRange.root.visibility = View.GONE
                    viewCriteria.root.visibility = View.GONE

                    viewJobRole.apply {
                        tvTitle.text = getString(R.string.job_role_cat)
                        tvDesc.text = job.jobRole
                    }
                    viewRequirements.apply {
                        tvTitle.text = getString(R.string.requirement_cat)
                        tvDesc.text = job.totalDaysRequired.plus(" Days")
                    }
                    viewProjectType.apply {
                        tvTitle.text = getString(R.string.project_type_cat)
                        tvDesc.text = job.projectType
                    }
                    viewJobDesc.apply {
                        tvTitle.text = getString(R.string.work_description_cat)
                        tvDesc.text = job.workDesc
                    }
                    viewJobLocation.apply {
                        tvTitle.text = getString(R.string.project_location_cat)
                        tvDesc.text = job.locationName
                    }
                }
            }

            viewCompanyName.apply {
                tvTitle.text = getString(R.string.company_name_cat)
                tvDesc.text = job.companyName
            }
            viewPersonDesignation.apply {
                tvTitle.text = getString(R.string.person_and_designation_cat)
                "${job.contactPersonName}, ${job.designation}".also { tvDesc.text = it }
            }
            viewContactDetails.apply {
                tvTitle.text = getString(R.string.contact_person_cat)
                tvDesc.text = job.mobileNumber
            }
            viewJobCreatedOn.apply {
                tvTitle.text = getString(R.string.job_created_on_cat)
                tvDesc.text = job.jobCreatedDate
            }
        }
    }

    private fun showProgress(show: Boolean) {
        progressBarBinding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    companion object {

        @JvmStatic
        fun newInstance() = ViewJobDetailsFragment().apply {
            arguments = Bundle()
        }
    }
}