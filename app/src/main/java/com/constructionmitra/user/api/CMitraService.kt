package com.constructionmitra.user.api

import com.constructionmitra.user.data.*
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
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
        @FieldMap(encoded = true) map: HashMap<String, String>
    ): BaseResponse<Any>

    @GET("api/v1/common/active_locations")
    suspend fun activeLocations(
    ): BaseResponse<List<Location>>

    @GET("api/v1/common/experience")
    suspend fun workExpOptions(
    ): BaseResponse<List<WorkExperience>>

    @Multipart
    @POST("api/v1/user/add_work_history")
    suspend fun addWork(
        @Part("user_id") userId: Int,
        @Part file: MultipartBody.Part,
    ): BaseResponse<Any>

    @Multipart
    @POST("api/v1/user/update_profile")
    suspend fun updateProfilePic(
        @Part("user_id") userId: Int,
        @Part("token") token: String,
        @Part file: MultipartBody.Part,
    ): BaseResponse<Any>

    @Multipart
    @POST("api/v1/user/update_profile")
    suspend fun updateLetterHead(
        @Part("user_id") userId: Int,
        @Part("token") token: String,
        @Part file: MultipartBody.Part,
    ): BaseResponse<Any>

    @FormUrlEncoded
    @POST("api/v1/user/work_history")
    suspend fun workHistory(
        @Field("user_id") userId: String
    ): BaseResponse<List<WorkHistory>>

    // Work
    @GET("api/v1/common/job_roles/{jobCategory}")
    suspend fun getJobRoles(
        @Path("jobCategory") jobCategory: String
    ): BaseResponse<List<JobRole>>

    @GET("api/v1/common/job_categories")
    suspend fun jobCategories(
    ): BaseResponse<List<JobCategory>>

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


    /**
     *  Contractor apis
     */

    @FormUrlEncoded
    @POST("api/v1/common/add_job_work")
    suspend fun addJobWork(
        @Field("user_id") userId: Int,
        @Field("job_category_id") jobCategoryId: String,
        @Field("job_role_id") jobRoleId: String,
        @Field("no_of_workers") numOfWorker: Int,
        @Field("job_post_id") jobPostId: Int,
    ): BaseResponse<JobPostId>

    @GET("api/v1/common/project_types")
    suspend fun projectTypes(
    ): BaseResponse<List<ProjectType>>


    @FormUrlEncoded
    @POST("api/v1/common/manage_job")
    suspend fun postAJob(
        @FieldMap map: HashMap<String, String>
    ): BaseResponse<Any>

    @FormUrlEncoded
    @POST("api/v1/common/total_job_post")
    suspend fun fetchProfileContractor(
        @Field("user_id") userId: String
    ): BaseResponse<ProfileDataContractor>


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