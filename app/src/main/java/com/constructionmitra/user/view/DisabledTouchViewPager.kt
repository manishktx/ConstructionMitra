package com.constructionmitra.user.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class DisabledTouchViewPager : ViewPager {
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)


    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }
}