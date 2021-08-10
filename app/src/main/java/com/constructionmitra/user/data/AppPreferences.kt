package com.constructionmitra.user.data

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

// todo: make it abstracted
@Singleton
class AppPreferences @Inject constructor(
    private val sharedPreferences: SharedPreferences
){
    fun  saveUserDetails(
        userId: String,
        userRole: String,
        token: String,
    ){
        sharedPreferences.edit().apply {
            putString(USER_ID, userId)
            putString(USER_ROLE, userRole)
            putString(TOKEN, token)
        }.apply()
    }

    fun getToken() = sharedPreferences.getString(TOKEN, "")

    fun getUserId() = sharedPreferences.getString(USER_ID, "")

    fun getUserRole() = sharedPreferences.getString(USER_ROLE, "")


    companion object{
        const val USER_ID = "user_id"
        const val USER_ROLE = "user_role"
        const val TOKEN = "token"
    }
}