package com.constructionmitra.user.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Job(
    @SerializedName("company_name")
    val companyName: String,
    @SerializedName("contact_person_name")
    val contactPersonName: String,
    @SerializedName("date_time")
    val dateTime: String,
    @SerializedName("designation")
    val designation: String,
    @SerializedName("email_id")
    val emailId: String,
    @SerializedName("is_published")
    val isPublished: String,
    @SerializedName("is_verified")
    val isVerified: String,
    @SerializedName("job_post_id")
    val jobPostId: String,
    @SerializedName("job_role")
    val jobRole: String,
    @SerializedName("job_valid_till")
    val jobValidTill: String,
    @SerializedName("mobile_number")
    val mobileNumber: String,
    @SerializedName("project_location")
    val projectLocation: String,
    @SerializedName("project_location_hn")
    val projectLocationHn: String,
    @SerializedName("project_type")
    val projectType: String?= null,
    @SerializedName("required_days")
    val requiredDays: String,
    @SerializedName("work_description")
    val workDescription: String
): Parcelable {

}