package com.constructionmitra.user.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostedJob(
    @SerializedName("applicant_data")
    val applicantData: ArrayList<ApplicantData>,
    @SerializedName("category_name")
    val categoryName: String,
    @SerializedName("company_name")
    val companyName: String,
    @SerializedName("contact_person_name")
    val contactPersonName: String,
    @SerializedName("location_name")
    val locationName: String,
    @SerializedName("date_time")
    val dateTime: String,
    @SerializedName("designation")
    val designation: String,
    @SerializedName("earlier_work")
    val earlierWork: String,
    @SerializedName("Gender")
    val gender: String,
    @SerializedName("job_created_on")
    val jobCreatedOn: String,
    @SerializedName("job_icon")
    val jobIcon: String,
    @SerializedName("job_post_id")
    val jobPostId: String,
    @SerializedName("job_role")
    val jobRole: String,
    @SerializedName("job_role_hn")
    val jobRoleHn: String,
    @SerializedName("job_varify_status")
    val jobVarifyStatus: String,
    @SerializedName("no_of_workers")
    val noOfWorkers: String,
    @SerializedName("number_of_openings")
    val numberOfOpenings: String,
    @SerializedName("project_name")
    val projectName: String,
    @SerializedName("total_applied_by_user")
    val totalAppliedByUser: String,
    @SerializedName("total_days_required")
    val totalDaysRequired: String,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("user_name")
    val userName: String,
    @SerializedName("work_id")
    val workId: String,
    @SerializedName("work_description")
    val workDesc: String,
    @SerializedName("Job_work_status")
    val jobWorkStatus: String,
    @SerializedName("is_published")
    val isPublished: Boolean = false,
    @SerializedName("min_salary")
    val minSalary: Int,
    @SerializedName("max_salary")
    val maxSalary: Int,
) : Parcelable
