package com.constructionmitra.user.api

import com.constructionmitra.user.data.AboutData
import com.constructionmitra.user.data.ContractorAboutData
import com.constructionmitra.user.data.WorkExperience
import javax.inject.Inject

class ProfileRequestsImpl @Inject constructor() : ProfileRequests{

    override fun updateAddress(
        token: String,
        userId: String,
        address: String?,
        currentAddress: String?,
    ): HashMap<String, String> {
        val hashMap = HashMap<String, String>().apply {
            put(ProfileRequests.USER_ID, userId)
            put(ProfileRequests.TOKEN, token)
        }
        address?.let {
            hashMap.put(ProfileRequests.PARAM_ADDRESS, address)
        }
        currentAddress?.let {
            hashMap.put(ProfileRequests.PARAM_CURRENT_ADDRESS, currentAddress)
        }
        return hashMap
    }

    override fun updateLocation(
        token: String,
        userId: String,
        preferredWorkLocations: String?,
    ): HashMap<String, String> {
        val hashMap = HashMap<String, String>().apply {
            put(ProfileRequests.USER_ID, userId)
            put(ProfileRequests.TOKEN, token)
        }
        preferredWorkLocations?.let {
            hashMap.put(ProfileRequests.PREFERRED_WORK_LOCATIONS, preferredWorkLocations)
        }
        return hashMap
    }

    override fun updateExp(token: String, userId: String, exp: WorkExperience): HashMap<String, String> {
        val hashMap = HashMap<String, String>().apply {
            put(ProfileRequests.USER_ID, userId)
            put(ProfileRequests.TOKEN, token)
        }
        exp?.let {
            hashMap.put(ProfileRequests.EXPERIENCE, exp.experience)
        }
        return hashMap
    }

    override fun updateAbout(
        userId: String,
        token: String,
        aboutData: AboutData
    ) : HashMap<String, String>{
        return hashMapOf(
            ProfileRequests.USER_ID to userId,
            ProfileRequests.TOKEN to token,
            ProfileRequests.PARAM_FULL_NAME to aboutData.name,
            ProfileRequests.PARAM_PHONE_NUM to aboutData.phoneNum,
            ProfileRequests.PARAM_OTHER_PHONE_NUM to aboutData.otherMobileNum,
            ProfileRequests.PARAM_AGE to aboutData.age,
            ProfileRequests.PARAM_GENDER to aboutData.gender,
            ProfileRequests.PARAM_HOME_ADDRESS to aboutData.homeAddress,
            ProfileRequests.PARAM_CURRENT_ADDRESS to aboutData.currentAddress,
        )
    }

    override fun updateContractorProfile(
        userId: String,
        token: String,
        data: ContractorAboutData,
    ) : HashMap<String, String>{
        return hashMapOf(
            ProfileRequests.USER_ID to userId,
            ProfileRequests.TOKEN to token,
            ProfileRequests.COMPANY_NAME to data.companyName,
            ProfileRequests.COMPANY_ADDRESS to data.companyAddress,
            ProfileRequests.COMPANY_CITY to data.city,
            ProfileRequests.DESIGNATION to data.designation,
            ProfileRequests.EMAIL to data.emailId,
            ProfileRequests.MOBILE_NUMBER to data.mobileNumber,
            ProfileRequests.EMPLOYEE_NAME to data.employeeName,
        )
    }
}