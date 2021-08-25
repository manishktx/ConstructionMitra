package com.constructionmitra.user.repository

import com.constructionmitra.user.api.CMitraService
import com.constructionmitra.user.api.Failure
import com.constructionmitra.user.api.Result
import com.constructionmitra.user.api.Success
import com.constructionmitra.user.data.*
import com.constructionmitra.user.utilities.ServerConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.http.Field
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CMitraRepository  @Inject constructor(
    private  val cMitraService: CMitraService
){

    suspend fun  requestOtp(mobile: String, jobRole: String): Result<LoginResponse>{
        return try {
            Success(cMitraService.requestOtp(
                hashMapOf(
                    "user_role" to jobRole.toRequestBody("text/plain".toMediaTypeOrNull()),
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

    suspend fun jobRoles(jobCategory: String): Result<List<JobRole>>{
        return try {
           val baseResponse = cMitraService.getJobRoles(jobCategory)
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

    suspend fun jobCategories(): Result<List<JobCategory>>{
        return try {
            val baseResponse = cMitraService.jobCategories()
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

    suspend fun updateProfile(hashMap: HashMap<String, String>): Result<BaseResponse<Any>>{
        return try {
            Success(cMitraService.updateProfile(hashMap))
        }catch (exp: Exception){
            Timber.d("okhttp: ${exp.toString()}")
            Failure(exp)
        }
    }

    suspend fun getAvailableJobs(
        userId: String,
        limit: Int,
        offset: Int,
        locationId: String,
        availableJobs: Boolean = true
    ): Result<BaseResponse<List<Job>>>{
        val mapReq = hashMapOf<String, Any>(
            "user_id" to userId,
            "limit" to limit,
            "offset" to offset,
            "location_id" to locationId,
        )
        return try {
            Success(
                if(availableJobs) cMitraService.getAvailableJobs(mapReq) else
                    cMitraService.getAppliedJobs(mapReq)
            )
        }catch (exp: Exception){
            Timber.d("okhttp: ${exp.toString()}")
            Failure(exp)
        }
    }

    suspend fun updateJobRoles(userId: String, token: String, jobRoleIds: String): Result<BaseResponse<Any>>{
        return try {
            val mapReq = hashMapOf<String, String>(
                "user_id" to userId,
                "token" to token,
                "job_role_ids" to jobRoleIds,
            )
            Success(cMitraService.updateJobRoles(mapReq))
        }catch (exp: Exception){
            Timber.d("okhttp: ${exp.toString()}")
            Failure(exp)
        }
    }

    suspend fun mapJob(userId: String, jobId: String): Result<BaseResponse<Any>>{
        return try {
            Success(cMitraService.mapJob(
                userId = userId,
                jobPostId =  jobId
            ))
        }catch (exp: Exception){
            Timber.d("okhttp: ${exp.toString()}")
            Failure(exp)
        }
    }

    suspend fun activeLocations(): Result<BaseResponse<List<Location>>>{
        return try {
            Success(cMitraService.activeLocations())
        }catch (exp: Exception){
            Timber.d("okhttp: ${exp.toString()}")
            Failure(exp)
        }
    }

    suspend fun workExpOptions(): Result<BaseResponse<List<WorkExperience>>>{
        return try {
            Success(cMitraService.workExpOptions())
        }catch (exp: Exception){
            Timber.d("okhttp: ${exp.toString()}")
            Failure(exp)
        }
    }

    suspend fun addWork(userId: Int, file: MultipartBody.Part): Result<BaseResponse<Any>>{
        return try {
            Success(cMitraService.addWork(
                userId,
                file)
            )
        }catch (exp: Exception){
            Timber.d("okhttp: ${exp.toString()}")
            Failure(exp)
        }
    }

    suspend fun updateProfilePic(
        userId: Int,
        token: String,
        file: MultipartBody.Part
    ): Result<BaseResponse<Any>>{
        return try {
            Success(cMitraService.updateProfilePic(
                userId,
                token,
                file)
            )
        }catch (exp: Exception){
            Timber.d("okhttp: ${exp.toString()}")
            Failure(exp)
        }
    }

    suspend fun updateLetterHead(
        userId: Int,
        token: String,
        file: MultipartBody.Part
    ): Result<BaseResponse<Any>>{
        return try {
            Success(cMitraService.updateLetterHead(
                userId,
                token,
                file)
            )
        }catch (exp: Exception){
            Timber.d("okhttp: ${exp.toString()}")
            Failure(exp)
        }
    }

    suspend fun workHistory(
        userId: String,
    ): Result<BaseResponse<List<WorkHistory>>>{
        return try {
            Success(cMitraService.workHistory(userId))
        }catch (exp: Exception){
            Timber.d("okhttp: ${exp.toString()}")
            Failure(exp)
        }
    }

    /**
     *  Contractor apis
     */

    suspend fun addJobWork(
        userId: String,
        jobCategoryId: String,
        jobRoleId: String,
        numOfWorker: String,
        jobPostId: String
    ): Result<BaseResponse<JobPostId>>{
        return try {
            Success(cMitraService.addJobWork(
                userId = userId.toInt(),
                jobCategoryId = jobCategoryId,
                jobRoleId = jobRoleId,
                numOfWorker =  numOfWorker.toInt(),
                jobPostId = jobPostId.toInt()
            ))
        }catch (exp: Exception){
            Timber.d("okhttp: ${exp.toString()}")
            Failure(exp)
        }
    }

    suspend fun projectTypes(): Result<BaseResponse<List<ProjectType>>>{
        return try {
            Success(cMitraService.projectTypes())
        }catch (exp: Exception){
            Timber.d("okhttp: ${exp.toString()}")
            Failure(exp)
        }
    }

    suspend fun postAJob(hashMap: HashMap<String, String>): Result<BaseResponse<Any>>{
        return try {
            Success(cMitraService.postAJob(hashMap))
        }catch (exp: Exception){
            Timber.d("okhttp: ${exp.toString()}")
            Failure(exp)
        }
    }

    suspend fun fetchProfileContractor(userId: String): Result<BaseResponse<ProfileDataContractor>>{
        return try {
            Success(cMitraService.fetchProfileContractor(userId))
        }catch (exp: Exception){
            Timber.d("okhttp: ${exp.toString()}")
            Failure(exp)
        }
    }

}