package com.constructionmitra.user.api

import com.constructionmitra.user.data.AboutData
import com.constructionmitra.user.data.ContractorAboutData
import com.constructionmitra.user.data.WorkExperience
import com.constructionmitra.user.data.WorkPreference

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

    fun updateWorkPreference(
        token: String,
        userId: String,
        workPreference: WorkPreference
    ) : HashMap<String, String>

    fun updateAbout(
        userId: String,
        token: String,
        aboutData: AboutData
    ) : HashMap<String, String>

    fun updateContractorProfile(
        userId: String,
        token: String,
        aboutData: ContractorAboutData
    ): HashMap<String, String>

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
        const val  WORK_PREFERENCE_ID = "work_preference_id"
        const val  PARAM_IMAGE = "image"
        const val  PROFILE_PIC = "profile_pic"
        const val  LETTER_HEAD = "user_doc"

        // Contractor profile
        const val  EMPLOYEE_NAME = "full_name"
        const val  COMPANY_NAME = "firm_name"
        const val  EMAIL = "email"
        const val  MOBILE_NUMBER = PARAM_PHONE_NUM
        const val  COMPANY_ADDRESS = "address"
        const val  DESIGNATION = "designation"
        const val  COMPANY_CITY = "current_residence"
    }
}