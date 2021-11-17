package com.constructionmitra.user.data


import com.google.gson.annotations.SerializedName

data class WorkHistory(
    @SerializedName("image")
    val image: String,
    @SerializedName("work_history_id")
    val workHistoryId: String
)