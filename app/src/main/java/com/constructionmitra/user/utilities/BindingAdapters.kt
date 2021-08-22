package com.constructionmitra.user.utilities

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.constructionmitra.user.R

object BindingAdapters {

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

    @BindingAdapter("app:loadResizedImage")
    @JvmStatic
    fun loadResizedImage(view: ImageView, imageUrl: String?) {
        Glide.with(view).load(imageUrl)
            .thumbnail(
                Glide.with(view).load(imageUrl).apply(
                    RequestOptions().override(200)
                )
            )
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(view)
    }
}