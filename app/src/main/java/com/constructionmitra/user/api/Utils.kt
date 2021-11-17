package com.constructionmitra.user.api

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

sealed class  Gender(englishName: String, hindiName: String)
data class  MALE (val englishName: String = "Male", val hindiName: String = "पुरुष") : Gender(englishName, hindiName)
data class  FEMALE (val englishName: String = "Female", val hindiName: String = "इस्त्री") : Gender(englishName, hindiName)
data class  OTHER (val englishName: String = "Other", val hindiName: String = "अन्य") : Gender(englishName, hindiName)

class NetworkConnectionInterceptor(private val mContext: Context) :
    Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnected) {
            throw NoConnectivityException()
            // Throwing our custom exception 'NoConnectivityException'
        }
        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

    @Suppress("DEPRECATION")
    private val isConnected: Boolean
        get() {
            val connectivityManager =
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = connectivityManager.activeNetworkInfo
            return netInfo != null && netInfo.isConnected
        }

}

class NoConnectivityException : IOException() {

    // You can send any message whatever you want from here.
    override val message: String
        get() = "No internet connection"

}