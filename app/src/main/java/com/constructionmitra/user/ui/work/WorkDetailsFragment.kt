package com.constructionmitra.user.ui.work

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.constructionmitra.user.FragmentContainerActivity
import com.constructionmitra.user.R
import com.constructionmitra.user.data.AppPreferences
import com.constructionmitra.user.data.Job
import com.constructionmitra.user.databinding.FragmentRequestForWorkBinding
import com.constructionmitra.user.databinding.ProgressBarBinding
import com.constructionmitra.user.ui.contractor.viewmodels.JobPostViewModel
import com.constructionmitra.user.ui.dialogs.AlertDialogWith1ActionButton
import com.constructionmitra.user.utilities.AppUtils
import com.constructionmitra.user.utilities.BitmapUtils
import com.constructionmitra.user.utilities.FileUtils
import com.constructionmitra.user.utilities.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_review_job_employer.*
import kotlinx.android.synthetic.main.item_profile.*
import kotlinx.android.synthetic.main.item_work.view.*
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class WorkDetailsFragment : Fragment() {
    private var job: Job? = null
    private lateinit var binding: FragmentRequestForWorkBinding
    private lateinit var progressBarBinding: ProgressBarBinding
    private var isShowContact = false

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
        binding = FragmentRequestForWorkBinding.inflate(inflater, container, false).apply {
            progressBarBinding = ProgressBarBinding.bind(root)
            ivShare.setOnClickListener {
                it.isClickable = false
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    BitmapUtils.takeScreenShot(root, activity = requireActivity() as AppCompatActivity){
                        _bitmap ->
                        FileUtils.shareImageFile(requireContext(), FileUtils.saveBitmapToFile(_bitmap, requireContext()))
                    }
                }
                else{
                    val bitmap = BitmapUtils.takeScreenShot(root)
                    FileUtils.shareImageFile(requireContext(), BitmapUtils.saveBitmapToGallery(bitmap, requireContext()))
                }

                it.isClickable = true
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // If job is null means navigation done here from Contractor Job Post
        job ?: run {
            requireActivity().onBackPressedDispatcher.addCallback(
                viewLifecycleOwner,
                backPressedCallback
            )
            jobPostViewModel.onFragmentSelected(2)
            with(binding){
                tvActionButton.visibility = View.GONE
                tvReqForContact.visibility = View.GONE
                separator2.visibility = View.GONE
                ivShare.visibility = View.GONE
            }
            // bind data with UI
            jobPostViewModel.jobPostRequest?.let {
                bindTitlesForContractor(it.toJob())
            }
        }

        binding.tvReqForContact.text = HtmlCompat.fromHtml(
            getString(R.string.hint_req_for_number),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )

        job?.let {
            _job ->
            Timber.d("Job details are = $_job")
            bindEmployeeData(_job)
//            bindData(_job)
            binding.tvActionButton.setOnClickListener {
                showProgress(true)
                if(binding.tvActionButton.text == getString(R.string.go_back_to_home))
                    requireActivity().finish()
                else
                    viewModel.mapJob(appPreferences.getUserId(), _job.jobPostId)
            }
        }

        // Request for Job/Work
        viewModel.requestForWork.observe(viewLifecycleOwner){
            showProgress(false)
            if(it){
                binding.tvReqForContact.visibility = View.INVISIBLE
                isShowContact = true
                binding.contactDetails.tvShowPhoneNum.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.bg_button_green)
                binding.tvActionButton.text = getString(R.string.go_back_to_home)
                // show dialog with msg
                AlertDialogWith1ActionButton.newInstance(
                    HtmlCompat.fromHtml(
                        getString(R.string.congratulation_msg_on_work_req),
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    ).toString(),
                    "Ok"
                ){

                }.show(parentFragmentManager, "alert_dialog")
            }
            else
                binding.root.showToast("Something went wrong, please try again.")
        }
    }

    private fun bindEmployeeData(job: Job){
        with(binding){
            // set work type and details
            viewWorkDetails.apply {
                tvTitle.text = getString(R.string.work).plus(":")
                tvDesc.text = job.jobRole
            }

            viewTeamDetails.apply {
                tvTitle.text = getString(R.string.team_count_title).plus(":")
                tvDesc.text = job.noOfWorkers
            }

            viewDuration.apply {
                tvTitle.text = getString(R.string.when_u_needed_team).plus(":")
                tvDesc.text = getString(R.string.with_in_days_hn, job.requiredDays)
            }

            viewProjectName.apply {
                tvTitle.text = getString(R.string.project_text_hn).plus(":")
                tvDesc.text = job.projectType
            }

            viewJobDes.apply {
                tvTitle.text = getString(R.string.job_desc_hn).plus(":")
                tvDesc.text = job.workDescription
            }

            viewCompanyName.apply {
                tvTitle.text = getString(R.string.company_name_hn).plus(":")
                tvDesc.text = job.companyName
            }

            viewJobLocation.apply {
                tvTitle.text = getString(R.string.project_location_hn).plus(":")
                tvDesc.text = job.projectLocation
            }

            viewContractorDetails.apply {
                tvTitle.text = getString(R.string.contact_details_hn).plus(":")
                if(job.contactPersonName.isNullOrEmpty() && job.designation.isNullOrEmpty())
                    root.visibility = View.GONE
                tvDesc.text = job.contactPersonName.plus(",").plus(job.designation)
            }

            // Contact details
            contactDetails.tvTitle.text = getString(R.string.do_contact).plus(":")
            contactDetails.tvDesc.text =  ""
            contactDetails.tvShowPhoneNum.setOnClickListener {
                if(isShowContact) {
                    if(contactDetails.tvDesc.text.length >= 10) {
                        AppUtils.openDial(requireActivity(), job.mobileNumber)
                    }
                    else {
                        contactDetails.tvShowPhoneNum.text = getString(R.string.call_kare)
                        contactDetails.tvDesc.text = job.mobileNumber
                    }
                }
            }
            // request max duration
            requestMaxDuration.tvTitle.text = getString(R.string.request_for_work).plus(":")
        }
    }

    private fun bindTitlesForContractor(job: Job){

    }

    private fun showProgress(show: Boolean) {
        progressBarBinding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            WorkDetailsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}