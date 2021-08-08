package com.constructionmitra.user.utilities

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.view.animation.Animation
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar

fun AnimatorSet.addOnAnimationEnd(onAnimationEnd:() -> Unit){
    addListener(object : Animator.AnimatorListener{
        override fun onAnimationStart(animation: Animator?) {

        }

        override fun onAnimationEnd(animation: Animator?) {
            onAnimationEnd()
        }

        override fun onAnimationCancel(animation: Animator?) {

        }

        override fun onAnimationRepeat(animation: Animator?) {

        }
    })
}

fun Animation.addOnAnimationEnd(onAnimationEnd:() -> Unit){
    setAnimationListener(object : Animation.AnimationListener{
        override fun onAnimationStart(animation: Animation?) {

        }

        override fun onAnimationEnd(animation: Animation?) {
            onAnimationEnd()
        }

        override fun onAnimationRepeat(animation: Animation?) {

        }
    })
}

val Context.isConnected: Boolean
    get() {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                val nw = connectivityManager.activeNetwork ?: return false
                val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
                when {
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }
            else -> {
                // Use depreciated methods only on older devices
                val nwInfo = connectivityManager.activeNetworkInfo ?: return false
                nwInfo.isConnected
            }
        }
    }

fun ViewPager.onPageSelected(pageSelected: (position: Int) -> Unit){
    this.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int,
        ) {

        }

        override fun onPageSelected(position: Int) {
           pageSelected(position)
        }

        override fun onPageScrollStateChanged(state: Int) {
        }

    })
}

fun ViewPager2.onPageSelected(pageSelected: (position: Int) -> Unit){
    this.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            pageSelected(position)
        }
    })
}

fun View.showSnackBarShort(message: String){
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}

fun View.showSnackBarLong(message: String){
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}

fun View.showSnackBarIndefinite(message: String){
    Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE).show()
}