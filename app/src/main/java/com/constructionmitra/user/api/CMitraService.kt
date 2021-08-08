package com.constructionmitra.user.api

import com.constructionmitra.user.data.BaseResponse
import com.constructionmitra.user.data.JobRole
import com.constructionmitra.user.data.LoginResponse
import com.constructionmitra.user.data.VerifyOtpData
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

    @GET("api/v1/common/job_roles/1")
    suspend fun getJobRoles(): BaseResponse<List<JobRole>>

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