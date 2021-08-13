package com.constructionmitra.user.utilities

import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.constructionmitra.user.R

object  BindingAdapters {

    @BindingAdapter("app:src")
    @JvmStatic
    fun setImageUri(view: ImageView, @DrawableRes id: Int) {
        view.setImageResource(id)
    }

    @BindingAdapter("avatar")
    @JvmStatic
    fun loadImage(view: ImageView, imageUrl: String?) {
        Glide.with(view.context).load(imageUrl)
//            .placeholder(R.drawable.placeholder_chat_item_image)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}