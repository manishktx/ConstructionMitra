package com.constructionmitra.user.ui.dialogs

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.constructionmitra.user.R
import com.constructionmitra.user.databinding.DialogAppAlertBinding
import com.constructionmitra.user.databinding.DialogGetFirmDetailsBinding
import com.constructionmitra.user.utilities.showToast

class GetFirmDetailsDialog(
    val onClick:(firmName: String, numOfWorkers: String) -> Unit
) : DialogFragment() {

    lateinit var binding: DialogGetFirmDetailsBinding

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
        binding = DialogGetFirmDetailsBinding.inflate(inflater, container, false).apply {
            tvSubmit.setOnClickListener {
                if(isDetailsAreValid()){
                    dismiss()
                    onClick(binding.etFirmName.text.toString(), binding.etTeamCount.text.toString())
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
        binding.etFirmName.text.toString().trim().length > 3 &&
                binding.etTeamCount.text.toString().trim().isNotEmpty()

    companion object {

        @JvmStatic
        fun newInstance(onClick:(firmName: String, numOfWorkers: String) -> Unit) =
            GetFirmDetailsDialog(onClick).apply {
                arguments = Bundle().apply {

                }
            }
    }

}