package com.constructionmitra.user.data


import com.google.gson.annotations.SerializedName

data class ProfileDataContractor(
    @SerializedName("total_active_jobs")
    val totalActiveJobs: String,
    @SerializedName("total_applied_user")
    val totalAppliedUser: Int,
    @SerializedName("total_jobs")
    val totalJobs: Int,
    @SerializedName("total_jobs_data")
    val postedJobDataList: List<PostedJob>,
    @SerializedName("total_seen")
    val totalSeen: Int
)