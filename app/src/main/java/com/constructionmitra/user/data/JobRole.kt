package com.constructionmitra.user.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class JobRole(
    @SerializedName("job_role") val jobRole: String,
    @SerializedName("job_role_hn") val jobRoleHn: String,
    @SerializedName("job_role_image") val jobRoleImage: String,
    @SerializedName("role_id") val roleId: String,
    @SerializedName("color") val color: String? = null
): Parcelable{

    var isChecked = false

    override fun equals(other: Any?): Boolean {
        return jobRole == (other as JobRole).jobRole
    }
    override fun toString(): String {
        return jobRole
    }
}