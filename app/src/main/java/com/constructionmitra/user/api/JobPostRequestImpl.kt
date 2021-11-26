package com.constructionmitra.user.api

import com.constructionmitra.user.data.JobPostRequest
import java.lang.Exception
import javax.inject.Inject

class JobPostRequestImpl @Inject constructor(): JobPostRequestMapper {

    override fun toJostPostRequest(userId: String, jobPostRequest: JobPostRequest): HashMap<String, String?> {
        jobPostRequest.jobRoleDetails ?: throw  Exception("Job roles details can not be null")
        jobPostRequest.employeeDetails ?: throw  Exception("Employee details can not be null")

        val requestMap = hashMapOf<String, String?>(
            JobPostRequestMapper.USER_ID to userId,
            JobPostRequestMapper.JOB_CATEGORY_ID to jobPostRequest.jobCategoryId,
            JobPostRequestMapper.JOB_POST_ID to (jobPostRequest.jobPostId?.jobPostId ?: "0"),
            JobPostRequestMapper.REQUIRED_DAYS to jobPostRequest.jobRoleDetails?.requiredDays,
            JobPostRequestMapper.PROJECT_TYPE_ID to jobPostRequest.jobRoleDetails?.projectId,
            JobPostRequestMapper.WORK_DESC to jobPostRequest.jobRoleDetails?.workDesc,
            JobPostRequestMapper.COMPANY_NAME to jobPostRequest.employeeDetails?.companyName,
            JobPostRequestMapper.CONTACT_PERSON_NAME to jobPostRequest.employeeDetails?.contactPerson,
            JobPostRequestMapper.DESIGNATION to jobPostRequest.employeeDetails?.designation!!,
            JobPostRequestMapper.MOBILE_NUMBER to jobPostRequest.employeeDetails?.mobileNumber,
            JobPostRequestMapper.EMAIL_ID to jobPostRequest.employeeDetails?.emailId,
            JobPostRequestMapper.PROJECT_NAME to jobPostRequest.employeeDetails?.projectName,
            JobPostRequestMapper.PROJECT_LOCATION_ID to jobPostRequest.employeeDetails?.projectLocationId,
            JobPostRequestMapper.IS_PUBLISHED to "0",
            // Added for SA
            JobPostRequestMapper.JOB_ROLE_ID to jobPostRequest.jobRoleDetails?.jobWorkId,
            JobPostRequestMapper.JOB_ROLE_ID to jobPostRequest.jobRoleDetails?.jobWorkId,
            JobPostRequestMapper.WORK_DONE_EARLIER to (jobPostRequest.jobRoleDetails?.workDoneEarlier),
            JobPostRequestMapper.MIN_EXP_REQUIRED to (jobPostRequest.jobRoleDetails?.minExpId),
            JobPostRequestMapper.MIN_QUALIFICATION_ID to (jobPostRequest.jobRoleDetails?.qualificationId),
            JobPostRequestMapper.NO_OF_OPENINGS to (jobPostRequest.jobRoleDetails?.noOfOpenings),
            JobPostRequestMapper.MIN_SALARY to (
                    (jobPostRequest.jobRoleDetails?.salaryRange)?.value?.split("-")?.first() ?: "0"
                    ),
            JobPostRequestMapper.MAX_SALARY to (
                    (jobPostRequest.jobRoleDetails?.salaryRange)?.value?.split("-")?.last() ?: "320000"
                    ),
        )
        jobPostRequest.jobRoleDetails?.let {  jobRoleDetails ->
            if(!jobRoleDetails.gender.isNullOrEmpty()){
                requestMap[JobPostRequestMapper.GENDER] = jobRoleDetails.gender
            }
        }

        return requestMap
    }
}