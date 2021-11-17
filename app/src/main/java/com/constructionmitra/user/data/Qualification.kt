package com.constructionmitra.user.data


import com.google.gson.annotations.SerializedName

data class Qualification(
    @SerializedName("qualification")
    val qualification: String,
    @SerializedName("qualification_hn")
    val qualificationHn: String,
    @SerializedName("qualification_id")
    val qualificationId: String
)