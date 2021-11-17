package com.constructionmitra.user.utilities.ext

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.constructionmitra.user.MyApplication

fun Fragment.app(): MyApplication{
    return requireActivity().application as MyApplication
}

fun Fragment.setDestination(
    @IdRes containerViewId: Int,
    @NavigationRes navGraphId: Int,
    @IdRes startDestId: Int,
    bundle: Bundle? = null
){
    (childFragmentManager.findFragmentById(containerViewId) as NavHostFragment).apply {
        val graph = navController.navInflater.inflate(navGraphId)
        graph.startDestination = startDestId
        if(bundle != null)
            navController.setGraph(graph, bundle)
        else
            navController.graph = graph
    }
}