package com.constructionmitra.user.data

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: OtpData,
) {
}

class  OtpData(
    @SerializedName("id", alternate = ["user_id"]) val id: String,
    @SerializedName("otp") val otp: String,
)