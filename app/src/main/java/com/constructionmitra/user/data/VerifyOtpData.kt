package com.constructionmitra.user.data

import com.google.gson.annotations.SerializedName

class VerifyOtpData(
    @SerializedName("user_id") val userId: String,
    @SerializedName("user_role") val userRole: String,
    @SerializedName("token") val token: String,
) {
}