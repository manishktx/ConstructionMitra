package com.constructionmitra.user.ui.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.constructionmitra.user.R
import com.constructionmitra.user.databinding.DialogAgencyFirmDetailBinding
import com.constructionmitra.user.databinding.DialogGetFirmDetailsBinding
import com.constructionmitra.user.utilities.showToast

class GetAgencyDetailsDialog(
    val onClick:(agencyName: String, agencyDesc: String) -> Unit
) : DialogFragment() {

    lateinit var binding: DialogAgencyFirmDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.let {
            it.setGravity(Gravity.CENTER)
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            it.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DialogAgencyFirmDetailBinding.inflate(inflater, container, false).apply {
            tvSubmit.setOnClickListener {
                if(isDetailsAreValid()){
                    dismiss()
                    onClick(binding.etFirmName.text.toString(), binding.etDescription.text.toString())
                }
                else{
                    binding.root.showToast(getString(R.string.enter_valid_details))
                }
            }
        }
        context ?: return binding.root
        return binding.root
    }

    private fun isDetailsAreValid() =
        binding.etFirmName.text.toString().trim().isNotEmpty() &&
                binding.etDescription.text.toString().trim().length > 10

    companion object {

        @JvmStatic
        fun newInstance(onClick:(agencyName: String, agencyDesc: String) -> Unit) =
            GetAgencyDetailsDialog(onClick).apply {
                arguments = Bundle().apply {

                }
            }
    }

}