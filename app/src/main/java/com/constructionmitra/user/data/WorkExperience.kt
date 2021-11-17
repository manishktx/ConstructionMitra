package com.constructionmitra.user.data


import com.google.gson.annotations.SerializedName

data class WorkExperience(
    @SerializedName("experience")
    val experience: String,
    @SerializedName("experience_hn")
    val experienceHn: String,
    @SerializedName("experience_id")
    val experienceId: String
)