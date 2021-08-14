package com.constructionmitra.user.ui.work

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.constructionmitra.user.FragmentContainerActivity
import com.constructionmitra.user.R
import com.constructionmitra.user.data.AppPreferences
import com.constructionmitra.user.data.Job
import com.constructionmitra.user.databinding.FragmentRequestForWorkBinding
import com.constructionmitra.user.databinding.ProgressBarBinding
import com.constructionmitra.user.ui.dialogs.AlertDialogWith1ActionButton
import com.constructionmitra.user.utilities.showToast
import dagger.hilt.android.AndroidEntryPoint
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            job = it.getParcelable(FragmentContainerActivity.PARCELABLE_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRequestForWorkBinding.inflate(inflater, container, false).apply {
            progressBarBinding = ProgressBarBinding.bind(root)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvReqForContact.text = HtmlCompat.fromHtml(
            getString(R.string.hint_req_for_number),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )

        job?.let {
            _job ->
            Timber.d("Job details are = $_job")
            bindData(_job)
            binding.tvActionButton.setOnClickListener {
                showProgress(true)
                if(binding.tvActionButton.text == getString(R.string.go_back_to_home))
                    requireActivity().finish()
                else
                    viewModel.mapJob(appPreferences.getUserId()!!, _job.jobPostId)
            }
        }

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

    private fun bindData(job: Job) {
        with(binding){
            // set work type and details
            viewWorkDetails.tvTitle.text = getString(R.string.work).plus(":")
            viewWorkDetails.tvDesc.text = job.projectType

            // set team and details
            viewTeamDetails.tvTitle.text = getString(R.string.team_count_title).plus(":")
            viewTeamDetails.tvDesc.text =  "15"

            // set duration
            viewDuration.tvTitle.text = getString(R.string.when_u_needed_team).plus(":")
            viewDuration.tvDesc.text =  getString(R.string.with_in_days, job.requiredDays)

            // set job details
            viewProjectDetails.tvTitle.text = "कार्य विवरण".plus(":")
            viewProjectDetails.tvDesc.text =  job.workDescription

            // set job details
            companyName.tvTitle.text = "कंपनी का नाम".plus(":")
            companyName.tvDesc.text =  job.companyName

            // Work location
            workLocation.tvTitle.text = "परियोजना स्थल".plus(":")
            workLocation.tvDesc.text =  job.projectLocation

            // Contact details
            contactDetails.tvTitle.text = getString(R.string.do_contact).plus(":")
            contactDetails.tvDesc.text =  ""
            contactDetails.tvShowPhoneNum.setOnClickListener {
                if(isShowContact) {
                    contactDetails.tvDesc.text = job.mobileNumber
                }
            }

            // Contractor details
            contractorDetails.tvTitle.text = "संपर्क करने वाले का नाम और पता".plus(":")
            contractorDetails.tvDesc.text =  "${job.contactPersonName}, ${job.designation}"

            // request max duration
            requestMaxDuration.tvTitle.text = getString(R.string.request_for_work).plus(":")
            requestMaxDuration.tvDesc.text =  job.jobValidTill
        }
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