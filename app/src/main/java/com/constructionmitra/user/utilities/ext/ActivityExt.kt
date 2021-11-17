package com.constructionmitra.user.utilities.ext

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.constructionmitra.user.MyApplication

fun Activity.app(){
    application as MyApplication
}

fun Activity.makeStatusBarTransparent() {
    window.apply {
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        statusBarColor = Color.TRANSPARENT
    }
}

fun Activity.hideKeyboard(): Boolean {
    val view = currentFocus
    view?.let {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
        return inputMethodManager.hideSoftInputFromWindow(view.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS)
    }
    return false
}

fun AppCompatActivity.setDestination(
    @IdRes fragmentContainerViewId: Int,
    @NavigationRes navGraphId: Int,
    @IdRes startDestId: Int,
    bundle: Bundle? = null
){
    findNavController(fragmentContainerViewId).apply {
        val graph = navInflater.inflate(navGraphId)
        graph.startDestination = startDestId
        if(bundle != null)
            setGraph(graph, bundle)
        else
            setGraph(graph, bundle)
    }
}