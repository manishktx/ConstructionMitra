package com.constructionmitra.user.data


import com.google.gson.annotations.SerializedName

data class JobRole(
    @SerializedName("job_role") val jobRole: String,
    @SerializedName("job_role_hn") val jobRoleHn: String,
    @SerializedName("job_role_image") val jobRoleImage: String,
    @SerializedName("role_id") val roleId: String
)