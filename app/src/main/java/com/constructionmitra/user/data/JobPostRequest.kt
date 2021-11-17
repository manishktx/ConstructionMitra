package com.constructionmitra.user.data

import com.constructionmitra.user.utilities.AppUtils

data class JobPostRequest(
    var selectedWorkList: List<SelectWorkData>? = null,
    var jobPostId: JobPostId? = null,
    var jobRoleDetails: JobRoleDetails? = null,
    var jobCategoryId: String? = null,
    var employeeDetails: EmployeeDetails? = null,
)
{
    private val selectedWorkListString: String?
        get() = if(selectedWorkList == null) "" else
        AppUtils.getSelectedWorkString(selectedWorkList!!)

    fun toJob(): Job{
        return Job(
            companyName = employeeDetails?.companyName ?: "",
            contactPersonName = employeeDetails?.contactPerson ?: "",
            dateTime = jobRoleDetails?.requiredDays ?: "",
            designation = employeeDetails?.designation ?: "",
            emailId = employeeDetails?.emailId ?: "",
            isPublished = "false",
            isVerified = "false",
            jobPostId =  jobPostId?.jobPostId ?: "",
            jobRole =  selectedWorkListString ?: "",
            mobileNumber = employeeDetails?.mobileNumber ?: "",
            jobValidTill = jobRoleDetails?.requiredDays ?: "",
            projectLocation = employeeDetails?.projectLocation ?: "",
            projectLocationHn = employeeDetails?.projectLocation ?: "",
            projectType = employeeDetails?.projectName ?: "",
            requiredDays = jobRoleDetails?.requiredDays ?: "",
            workDescription = jobRoleDetails?.workDesc ?: "",
        )
    }
}

data class  JobRoleDetails(
    val requiredDays: String,
    val projectId: String,
    val workDesc: String,
)

data class  EmployeeDetails(
    val companyName: String,
    val contactPerson: String,
    var designation: String? = null,
    val mobileNumber: String,
    val emailId: String,
    var projectName: String? = null,
    val projectLocation: String,
    val projectLocationId: String,
)