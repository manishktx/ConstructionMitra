package com.constructionmitra.user.ui.dialogs

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.constructionmitra.user.databinding.DialogAlertWithOneActionButtonBinding
import com.constructionmitra.user.databinding.DialogAppAlertBinding

class AlertDialogWith1ActionButton(val onClick:() -> Unit) : DialogFragment() {

    private var message: String? = null
    private var actionButtonText: String? = null
    lateinit var binding: DialogAlertWithOneActionButtonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            actionButtonText = it.getString(KEY_ACTION_BUTTON_TEXT)
            message = it.getString(KEY_MSG)
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
        context ?: return binding.root
        binding = DialogAlertWithOneActionButtonBinding.inflate(inflater, container, false).apply {
            tvActionButton.text = actionButtonText ?: "ok"
            tvMessage.text = message ?: "Success!"
            tvActionButton.setOnClickListener {
                dismiss()
                onClick()
            }
        }
        return binding.root
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }

    companion object {
        private const val KEY_MSG = "key_msg"
        private const val KEY_ACTION_BUTTON_TEXT = "key_action_button"
        @JvmStatic
        fun newInstance(
            message: String,
            actionButtonText: String,
            onClick:() -> Unit) =
            AlertDialogWith1ActionButton(onClick).apply {
                arguments = Bundle().apply {
                    putString(KEY_MSG, message)
                    putString(KEY_ACTION_BUTTON_TEXT, actionButtonText)
                }
            }
    }

}