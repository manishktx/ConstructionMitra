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
        name: String,
        mobile: String,
    ){
        sharedPreferences.edit().apply {
            putInt(USER_ID, userId.toInt())
            putString(USER_ROLE, userRole)
            putString(TOKEN, token)
            putString(NAME, name)
            putString(MOBILE, mobile)
        }.apply()
    }

    fun  saveEmployerDetails(
        companyName: String? = null,
        emailId: String? = null,
    ){
        sharedPreferences.edit().apply {
            if(!companyName.isNullOrEmpty())
                putString(COMPANY_NAME, companyName)
            if(!emailId.isNullOrEmpty())
                putString(EMAIL_ID, emailId)
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

    fun getDesignation() = sharedPreferences.getString(DESIGNATION, "")

    fun getUserId() = sharedPreferences.getInt(USER_ID, 0).toString()

    fun getUserName() = sharedPreferences.getString(NAME, "").toString()

    fun getMobileNumber() = sharedPreferences.getString(MOBILE, "").toString()

    fun getCompanyName() = sharedPreferences.getString(COMPANY_NAME, "").toString()

    fun getEmailId() = sharedPreferences.getString(EMAIL_ID, "").toString()

    fun getUserRole() = sharedPreferences.getString(USER_ROLE, "")

    fun saveProfile(profileData: ProfileData){
        val profile = Gson().toJson(profileData)
        if(!profileData.firmName.isNullOrEmpty()){
            saveEmployerDetails(companyName = profileData.firmName)
        }

        if(!profileData.email.isNullOrEmpty()){
            saveEmployerDetails(emailId = profileData.email)
        }
        saveUserName(profileData.fullName)
        saveMobileNumber(profileData.phoneNumber)
        saveDesignation(profileData.designation)
        sharedPreferences.edit().putString(PROFILE, profile).apply()
    }

    private fun saveUserName(userName: String?){
        if(!userName.isNullOrEmpty()){
            sharedPreferences.edit().putString(NAME, userName).apply()
        }
    }

    private fun saveDesignation(designation: String?){
        if(!designation.isNullOrEmpty()){
            sharedPreferences.edit().putString(DESIGNATION, designation).apply()
        }
    }

    private fun saveMobileNumber(mobileNumber: String?){
        if(!mobileNumber.isNullOrEmpty()){
            sharedPreferences.edit().putString(MOBILE, mobileNumber).apply()
        }
    }

    companion object{
        const val USER_ID = "user_id"
        const val USER_ROLE = "user_role"
        const val TOKEN = "token"
        const val NAME = "_name"
        const val MOBILE = "_mobile"
        const val USER_TYPE = "user_type"
        const val DESIGNATION = "designation"
        const val COMPANY_NAME = "company_name"
        const val EMAIL_ID = "email_id"
        private const val PROFILE = "profile"
        const val IS_NEW_CONTRACTOR = "new_contractor"
    }
}