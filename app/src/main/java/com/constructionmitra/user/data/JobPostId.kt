package com.constructionmitra.user.data


import com.google.gson.annotations.SerializedName

data class JobPostId(
    @SerializedName("job_post_id")
    val jobPostId: String,
    @SerializedName("job_work_id")
    val jobWorkId: Int
)