package com.constructionmitra.user.api

import android.content.Context
import com.constructionmitra.user.data.AboutData
import com.constructionmitra.user.data.WorkExperience

interface ProfileRequests {

    fun updateAddress(
        token: String,
        userId: String,
        currentAddress: String?,
        permanentAddress: String?
    ) : HashMap<String, String>

    fun updateLocation(
        token: String,
        userId: String,
        preferredWorkLocations: String?
    ) : HashMap<String, String>

    fun updateExp(
        token: String,
        userId: String,
        exp: WorkExperience
    ) : HashMap<String, String>

    fun updateAbout(
        userId: String,
        token: String,
        aboutData: AboutData
    ) : HashMap<String, String>


    companion object{
        const val  PARAM_ADDRESS = "address"
        const val  PARAM_FULL_NAME = "full_name"
        const val  PARAM_PHONE_NUM = "phone_number"
        const val  PARAM_OTHER_PHONE_NUM = "other_phone_number"
        const val  PARAM_GENDER = "gender"
        const val  PARAM_AGE = "age"
        const val  PARAM_HOME_ADDRESS = "address"
        const val  PARAM_CURRENT_ADDRESS = "current_residence"

        const val  USER_ID = "user_id"
        const val  TOKEN = "token"
        const val  PREFERRED_WORK_LOCATIONS = "preffered_location"
        const val  EXPERIENCE = "experience"
        const val  PARAM_IMAGE = "image"
    }
}