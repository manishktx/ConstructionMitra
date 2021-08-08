package com.constructionmitra.user.data

import com.google.gson.annotations.SerializedName

data class BaseResponse<out T: Any>(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: T?,
) {
}