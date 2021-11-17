package com.constructionmitra.user.data


import com.google.gson.annotations.SerializedName

data class AppConfig(
    @SerializedName("data")
    val configConfigData : ConfigData,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)