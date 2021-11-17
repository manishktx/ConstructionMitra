package com.constructionmitra.user.ui.dialogs

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import com.constructionmitra.user.R
import com.constructionmitra.user.databinding.DialogLogoutBinding
import com.constructionmitra.user.utilities.AppUtils

class LogoutDialog(
    private val ctx: Context,
    private val onNo:() -> Unit, private val onYes: () -> Unit
) : Dialog(ctx) {

    private lateinit var binding: DialogLogoutBinding
    private val inflater: LayoutInflater = LayoutInflater.from(ctx)

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogLogoutBinding.inflate(inflater).apply {
            setContentView(root)
        }
        val width = (ctx.resources.displayMetrics.widthPixels * 0.95).toInt()
        window!!.setLayout(
            width,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        window!!.setBackgroundDrawableResource(android.R.color.transparent)
        setCanceledOnTouchOutside(false)
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.tvNo.setOnClickListener {
            AppUtils.preventTwoClick(it)
            onNo()
            dismiss()
        }

        binding.tvYes.setOnClickListener {
            AppUtils.preventTwoClick(it)
            onYes()
            dismiss()
        }
    }

    override fun show() {
        window?.attributes?.windowAnimations = R.style.Dialog_Back
        super.show()
    }

    companion object {
        fun newInstance(
            context: Context, onNo:() -> Unit, onYes: () -> Unit
        ) = LogoutDialog(context, onNo, onYes)
    }
}
