package com.constructionmitra.user

import android.app.Application
import android.graphics.Bitmap
import com.constructionmitra.user.data.AppConfig
import com.constructionmitra.user.data.ConfigData
import com.constructionmitra.user.utilities.constants.AppConstants
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApplication : Application() {
    private var _appConfigData: ConfigData? = null
    val appConfigData
    get() = _appConfigData

    override fun onCreate() {
        super.onCreate()
//        if (BuildConfig.DEBUG) {
            Timber.uprootAll()
            Timber.plant(Timber.DebugTree())
//        }
    }

    fun saveConfig(configData: ConfigData? = null){
        _appConfigData = configData ?: AppConstants.defaultAppConfig
    }
}