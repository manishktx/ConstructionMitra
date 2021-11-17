package com.constructionmitra.user.data


import com.google.gson.annotations.SerializedName

data class PostedJob(
    @SerializedName("applicant_data")
    val applicantData: List<Any>,
    @SerializedName("category_name")
    val categoryName: String,
    @SerializedName("company_name")
    val companyName: String,
    @SerializedName("contact_person_name")
    val contactPersonName: String,
    @SerializedName("date_time")
    val dateTime: String,
    @SerializedName("designation")
    val designation: String,
    @SerializedName("earlier_work")
    val earlierWork: String,
    @SerializedName("gender")
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
    val workDesc: String
)