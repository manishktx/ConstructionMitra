package com.constructionmitra.user.api

import com.constructionmitra.user.data.WorkExperience

interface ProfileRequests {

    fun updateAddress(token: String, userId: String, currentAddress: String?, permanentAddress: String?) : HashMap<String, String>

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



    companion object{
        const val  PARAM_ADDRESS = "address"
        const val  USER_ID = "user_id"
        const val  TOKEN = "token"
        const val  PARAM_CURRENT_ADDRESS = "current_residence"
        const val  PREFERRED_WORK_LOCATIONS = "preffered_location"
        const val  EXPERIENCE = "experience"
    }
}