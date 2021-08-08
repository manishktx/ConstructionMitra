package com.constructionmitra.user.ui.dialogs

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.constructionmitra.user.databinding.DialogAppAlertBinding
import com.constructionmitra.user.databinding.DialogGetFirmDetailsBinding

class GetFirmDetailsDialog(val onClick:() -> Unit) : DialogFragment() {

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
                dismiss()
                onClick()
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
    }

    companion object {

        @JvmStatic
        fun newInstance(onClick:() -> Unit) =
            GetFirmDetailsDialog(onClick).apply {
                arguments = Bundle().apply {

                }
            }
    }

}