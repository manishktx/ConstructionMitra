package com.constructionmitra.user.data

import android.content.SharedPreferences
import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton

// todo: make it abstracted
@Singleton
class AppPreferences @Inject constructor(
    private val sharedPreferences: SharedPreferences
){
    val profile: ProfileData?
    get() {
        val profile = sharedPreferences.getString(PROFILE, "")
        if(!profile.isNullOrEmpty()){
            return Gson().fromJson(profile, ProfileData::class.java)
        }
        return null
    }

    fun  saveUserDetails(
        userId: String,
        userRole: String,
        token: String,
    ){
        sharedPreferences.edit().apply {
            putInt(USER_ID, userId.toInt())
            putString(USER_ROLE, userRole)
            putString(TOKEN, token)
        }.apply()
    }

    fun saveBoolean(key: String, value: Boolean){
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun saveUserType(userType: String){
        sharedPreferences.edit().putString(USER_TYPE, userType).apply()
    }

    fun getUserType() = sharedPreferences.getString(USER_TYPE, "")

    fun getBoolean(key: String) = sharedPreferences.getBoolean(key, false)

    fun getToken() = sharedPreferences.getString(TOKEN, "")

    fun getUserId() = sharedPreferences.getInt(USER_ID, 0).toString()

    fun getUserRole() = sharedPreferences.getString(USER_ROLE, "")

    fun saveProfile(profileData: ProfileData){
        val profile = Gson().toJson(profileData)
        sharedPreferences.edit().putString(PROFILE, profile).apply()
    }

    companion object{
        const val USER_ID = "user_id"
        const val USER_ROLE = "user_role"
        const val TOKEN = "token"
        const val USER_TYPE = "user_type"
        private const val PROFILE = "profile"
        const val IS_NEW_CONTRACTOR = "new_contractor"
    }
}