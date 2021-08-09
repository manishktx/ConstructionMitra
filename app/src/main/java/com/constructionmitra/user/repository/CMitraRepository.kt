package com.constructionmitra.user.repository

import com.constructionmitra.user.api.CMitraService
import com.constructionmitra.user.api.Failure
import com.constructionmitra.user.api.Result
import com.constructionmitra.user.api.Success
import com.constructionmitra.user.data.*
import com.constructionmitra.user.utilities.ServerConstants
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CMitraRepository  @Inject constructor(
    private  val cMitraService: CMitraService
){

    suspend fun  requestOtp(mobile: String): Result<LoginResponse>{
        return try {
            Success(cMitraService.requestOtp(
                hashMapOf(
                    "user_role" to "4".toRequestBody("text/plain".toMediaTypeOrNull()),
                    "fcm_id" to  "ANDROIDKT".toRequestBody("text/plain".toMediaTypeOrNull()),
                    "phone_number" to mobile.toRequestBody("text/plain".toMediaTypeOrNull()),
                    "full_name" to "Manish".toRequestBody("text/plain".toMediaTypeOrNull())
                )
            ))
        }catch (exp: Exception){
            Timber.d("okhttp: ${exp.toString()}")
            Failure(exp)
        }
    }

    suspend fun verifyOtp(mobile: String, otp: String): Result<BaseResponse<VerifyOtpData>>{
        return try {
            Success(cMitraService.verifyOtp(
                phoneNumber = mobile,
                otp =  otp
            ))
        }catch (exp: Exception){
            Timber.d("okhttp: ${exp.toString()}")
            Failure(exp)
        }
    }

    suspend fun jobRoles(): Result<List<JobRole>>{
        return try {
           val baseResponse = cMitraService.getJobRoles()
            Timber.d("jobRoles: $baseResponse")
            if(baseResponse.status.equals(ServerConstants.STATUS_SUCCESS, ignoreCase = true)){
                Success(baseResponse.data!!)
            }
            else
                Failure(Exception(baseResponse.message))
        }catch (exp: Exception){
            Timber.d("okhttp: ${exp.toString()}")
            Failure(exp)
        }
    }

    suspend fun fetchProfile(userId: String, token: String): Result<BaseResponse<ProfileData>>{
        return try {
            Success(cMitraService.profileDetail(
                userId = userId,
                token = token
            ))
        }catch (exp: Exception){
            Timber.d("okhttp: ${exp.toString()}")
            Failure(exp)
        }
    }

    suspend fun getAvailableWork(hashMap: HashMap<String, String>): Result<BaseResponse<ProfileData>>{
        return try {
            Success(cMitraService.availableWork(
                hashMap
            ))
        }catch (exp: Exception){
            Timber.d("okhttp: ${exp.toString()}")
            Failure(exp)
        }
    }

    suspend fun updateProfile(hashMap: HashMap<String, String>): Result<BaseResponse<Any>>{
        return try {
            Success(cMitraService.updateProfile(hashMap))
        }catch (exp: Exception){
            Timber.d("okhttp: ${exp.toString()}")
            Failure(exp)
        }
    }
}