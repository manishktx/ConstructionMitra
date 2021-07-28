package com.constructionmitra.user.utilities

import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter

object  BindingAdapters {

    @BindingAdapter("app:src")
    @JvmStatic
    fun setImageUri(view: ImageView, @DrawableRes id: Int) {
        view.setImageResource(id)
    }
}