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
    val selectedWorkListString: String?
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
    val projectId: String? = "",
    val workDesc: String,
    var _minExpId: String? = null,
    var jobWorkId: String? = "",
    var _workDoneEarlier: String? = null,
    var minWorkValue: String? = null,
    var _gender: String? = null,
    var _minQualificationId: String? = null,
    var _noOfOpenings: String? = null,
    var _salaryRange: Salary? = null,
    var _classification: String? = null,
){
    val  workDoneEarlier
    get() = _workDoneEarlier ?: ""
    val  minExpId
        get() = _minExpId ?: ""
    val  qualificationId
        get() = _minQualificationId ?: ""
    val  gender
        get() = _gender ?: ""
    val  noOfOpenings
        get() = _noOfOpenings ?: "0"
    val  salaryRange
        get() = _salaryRange ?: Salary("0-3.2", "")
    val  classification
        get() = _classification ?: ""
}

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