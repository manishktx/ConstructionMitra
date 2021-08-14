package com.constructionmitra.user.api

import com.constructionmitra.user.data.*
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface CMitraService {

    @Multipart
    @POST("api/v1/user/login")
    suspend fun requestOtp(
        @PartMap map: HashMap<String, RequestBody>
    ): LoginResponse

    @FormUrlEncoded
    @POST("api/v1/user/verify_otp")
    suspend fun verifyOtp(
        @Field("phone_number") phoneNumber: String,
        @Field("otp") otp: String,
    ): BaseResponse<VerifyOtpData>

    // Profile
    @FormUrlEncoded
    @POST("api/v1/user/user_profile/")
    suspend fun profileDetail(
        @Field("user_id") userId: String,
        @Field("token") token: String,
    ): BaseResponse<ProfileData>

    @FormUrlEncoded
    @POST("api/v1/user/update_profile")
    suspend fun updateProfile(
        @FieldMap map: HashMap<String, String>
    ): BaseResponse<Any>

    @FormUrlEncoded
    @POST("api/v1/user/update_profile")
    suspend fun updateJobRoles(
        @FieldMap map: HashMap<String, String>
    ): BaseResponse<Any>

    @GET("api/v1/common/active_locations")
    suspend fun activeLocations(
    ): BaseResponse<List<Location>>

    @GET("api/v1/common/experience")
    suspend fun workExpOptions(
    ): BaseResponse<List<WorkExperience>>

    // Work
    @GET("api/v1/common/job_roles/{jobCategory}")
    suspend fun getJobRoles(
        @Path("jobCategory") jobCategory: String
    ): BaseResponse<List<JobRole>>

    @FormUrlEncoded
    @POST("api/v1/common/job_post_list/available")
    suspend fun getAvailableJobs(
        @FieldMap map: HashMap<String, Any>
    ): BaseResponse<List<Job>>

    @FormUrlEncoded
    @POST("api/v1/common/job_post_list/applied")
    suspend fun getAppliedJobs(
        @FieldMap map: HashMap<String, Any>
    ): BaseResponse<List<Job>>

    @FormUrlEncoded
    @POST("api/v1/common/job_mapping")
    suspend fun mapJob(
        @Field("user_id") userId: String,
        @Field("job_post_id") jobPostId: String,
    ): BaseResponse<Any>

    companion object {
        private const val BASE_URL = "http://creativemint.in/cmitra/"

        fun  create(): CMitraService{
            val interceptor =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CMitraService::class.java)
        }
    }
}