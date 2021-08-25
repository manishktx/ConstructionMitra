package com.constructionmitra.user.api

import com.constructionmitra.user.data.JobPostRequest
import java.lang.Exception
import javax.inject.Inject

class JobPostRequestImpl @Inject constructor(): JobPostRequestMapper {

    override fun toJostPostRequest(userId: String, jobPostRequest: JobPostRequest): HashMap<String, String> {
        jobPostRequest.jobRoleDetails ?: throw  Exception("Job roles details can not be null")
        jobPostRequest.employeeDetails ?: throw  Exception("Employee details can not be null")

        return hashMapOf<String, String>(
            JobPostRequestMapper.USER_ID to userId,
            JobPostRequestMapper.JOB_CATEGORY_ID to jobPostRequest.jobCategoryId!!,
            JobPostRequestMapper.Params.JOB_POST_ID to jobPostRequest.jobPostId?.jobPostId!!,
            JobPostRequestMapper.Params.REQUIRED_DAYS to jobPostRequest.jobRoleDetails!!.requiredDays,
            JobPostRequestMapper.Params.PROJECT_TYPE_ID to jobPostRequest.jobRoleDetails!!.projectId,
            JobPostRequestMapper.Params.WORK_DESC to jobPostRequest.jobRoleDetails!!.workDesc,
            JobPostRequestMapper.Params.COMPANY_NAME to jobPostRequest.employeeDetails!!.companyName,
            JobPostRequestMapper.Params.CONTACT_PERSON_NAME to jobPostRequest.employeeDetails!!.contactPerson,
            JobPostRequestMapper.Params.DESIGNATION to jobPostRequest.employeeDetails!!.designation!!,
            JobPostRequestMapper.Params.MOBILE_NUMBER to jobPostRequest.employeeDetails!!.mobileNumber,
            JobPostRequestMapper.Params.EMAIL_ID to jobPostRequest.employeeDetails!!.emailId,
            JobPostRequestMapper.Params.PROJECT_NAME to jobPostRequest.employeeDetails!!.projectName!!,
            JobPostRequestMapper.Params.PROJECT_LOCATION_ID to jobPostRequest.employeeDetails!!.projectLocationId,
            JobPostRequestMapper.Params.IS_PUBLISHED to "0",
        )
    }
}