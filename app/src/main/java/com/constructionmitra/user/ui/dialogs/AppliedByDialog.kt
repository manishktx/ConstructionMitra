package com.constructionmitra.user.ui.dialogs

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.constructionmitra.user.R
import com.constructionmitra.user.data.ApplicantData
import com.constructionmitra.user.databinding.DialogAppliedByBinding
import com.constructionmitra.user.databinding.HeaderAppliedByBinding
import com.constructionmitra.user.databinding.ItemAppliedByBinding
import com.constructionmitra.user.utilities.AppUtils
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import timber.log.Timber

class AppliedByDialog(
    private val onButtonClicked: () -> Unit,
    private val onDismiss: () -> Unit,
) : BottomSheetDialogFragment() {


    private var applicants: ArrayList<ApplicantData>? = null
    private var jobTitle: String? = null
    private var amount: String? = null
    private var celebName: String? = null
    private lateinit var binding: DialogAppliedByBinding

    override fun getTheme(): Int {
        return R.style.Transparent_BottomSheetDialogTheme
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            applicants = it.getParcelableArrayList<ApplicantData>(ARG_APPLICANTS)
            jobTitle = it.getString(ARG_JOB_TITLE, null)
        }
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), theme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DialogAppliedByBinding.inflate(inflater, container, false).apply {
                jobTitle?.let {
                    tvTitle.text = it
                }
                applicants?.let { arrayList ->
                     rvAppliedBy.adapter = AppliedByAdapter(activity as AppCompatActivity, arrayList){

                     }
                }
            }
        context ?: return binding.root
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismiss.invoke()
    }
    companion object{

        private const val  ARG_APPLICANTS = "arg_applicants"
        private const val  ARG_JOB_TITLE = "job_title"
        fun log(message: String) {
            Timber.d("RefaceBottomSheetDialog: $message")
        }

        @JvmStatic
        fun newInstance(
            jobTitle: String,
            list: ArrayList<ApplicantData>,
            onButtonClicked: () -> Unit,
            onDismissed: () -> Unit
        ) =
            AppliedByDialog(onButtonClicked, onDismissed).apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_APPLICANTS, list)
                    putString(ARG_JOB_TITLE, jobTitle)
                }
            }
    }
}

class AppliedByAdapter(
    private val activity: AppCompatActivity,
    private val list: List<ApplicantData>,
    private val onItemClick: (applicantData: ApplicantData) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType){
            VIEW_TYPE_HEADER ->  HeaderViewHolder(HeaderAppliedByBinding.inflate(inflater, parent, false))
            else -> {
                ViewHolder(ItemAppliedByBinding.inflate(inflater, parent, false))
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == 0) VIEW_TYPE_HEADER else VIEW_TYPE_ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(position != 0){
            (holder as ViewHolder).bindData(onItemClick = onItemClick, list[position - 1], position)
        }
    }


    inner class HeaderViewHolder(val binding: HeaderAppliedByBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    inner class ViewHolder(val binding: ItemAppliedByBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bindData(
            onItemClick: (applicantData: ApplicantData) -> Unit,
            applicantData: ApplicantData, position: Int
        ) {
            val context = binding.root.context
            with(binding){
                this.position = position.toString()
                this.data = applicantData
                if(applicantData.showContactDetails) {
                    tvViewContact.text = applicantData.phoneNumber
                    tvViewContact.setBackgroundResource(0)
                    tvViewContact.setTextColor(ContextCompat.getColor(context, R.color.grey_500))
                }
                else {
                    tvViewContact.text = context.getString(R.string.view_contact)
                    tvViewContact.background =
                        ContextCompat.getDrawable(context, R.drawable.bg_button_blue)
                    tvViewContact.setTextColor(ContextCompat.getColor(context, R.color.white))
                }
                this.tvViewContact.setOnClickListener {
                    if(!applicantData.showContactDetails)
                    {
                        applicantData.showContactDetails = true
                        notifyDataSetChanged()
                    }
                    else {
                        AppUtils.openDial(activity, applicantData.phoneNumber)
                    }
                }
                executePendingBindings()
            }
        }
    }

    companion object {
        const val  VIEW_TYPE_HEADER = 0x1
        const val  VIEW_TYPE_ITEM = 0x2
    }

}
