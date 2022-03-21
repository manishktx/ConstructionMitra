package com.constructionmitra.user.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(t: T, onIteClick: (t: T) -> Unit)
}