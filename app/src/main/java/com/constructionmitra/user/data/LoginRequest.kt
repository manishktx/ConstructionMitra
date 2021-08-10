package com.constructionmitra.user.data

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("user_role") val userRole: String,
    @SerializedName("fcm_id") val fcmId: String,
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("full_name") val fullName: String,
) {
}