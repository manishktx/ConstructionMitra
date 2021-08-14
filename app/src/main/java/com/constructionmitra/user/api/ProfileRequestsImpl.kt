package com.constructionmitra.user.api

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
}