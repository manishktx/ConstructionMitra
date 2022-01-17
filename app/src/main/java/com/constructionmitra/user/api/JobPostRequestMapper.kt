package com.constructionmitra.user.api

import com.constructionmitra.user.data.JobPostRequest

interface JobPostRequestMapper {
    fun toJostPostRequest(
        userId: String, jobPostRequest: JobPostRequest
    ) : HashMap<String, String?>

    companion object Params{
        const val  USER_ID =  ProfileRequests.USER_ID
        const val  JOB_CATEGORY_ID =  "job_category_id"
        const val  JOB_POST_ID =  "job_post_id"
        const val  JOB_ROLE_ID =  "job_role_id"
        const val  WORK_DONE_EARLIER =  "work_done_earlier"
        const val  REQUIRED_DAYS =  "required_days"
        const val  NO_OF_REQUIRED_DAYS =  "no_of_days_required"
        const val  PROJECT_TYPE_ID =  "project_type_id"
        const val  WORK_DESC =  "work_description"
        const val  JOB_DESC =  "job_description"
        const val  JOB_CLASSIFICATION =  "classification"
        const val  JOB_CRITERIA =  "criteria"
        const val  COMPANY_NAME =  "company_name"
        const val  CONTACT_PERSON_NAME =  "contact_person_name"
        const val  DESIGNATION =  "designation"
        const val  MOBILE_NUMBER =  "mobile_number"
        const val  EMAIL_ID =  "email_id"
        const val  PROJECT_NAME =  "project_name"
        const val  PROJECT_LOCATION_ID =  "project_location_id"
        const val  IS_PUBLISHED =  "is_published"
        const val  MIN_EXP_REQUIRED =  "minimum_experience_id"
        const val  MIN_QUALIFICATION_ID =  "minimum_qualification_id"
        const val  NO_OF_OPENINGS =  "number_of_openings"
        const val  MIN_SALARY =  "minimum_salary"
        const val  MAX_SALARY =  "maximum_salary"
        const val  GENDER =  "gender"

    }
}