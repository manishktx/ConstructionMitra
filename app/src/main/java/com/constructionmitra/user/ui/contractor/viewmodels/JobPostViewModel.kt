package com.constructionmitra.user.ui.contractor.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.constructionmitra.user.api.Failure
import com.constructionmitra.user.api.JobPostRequestMapper
import com.constructionmitra.user.api.Result
import com.constructionmitra.user.api.Success
import com.constructionmitra.user.data.*
import com.constructionmitra.user.repository.CMitraRepository
import com.constructionmitra.user.ui.base.BaseViewModel
import com.constructionmitra.user.utilities.ServerConstants
import com.constructionmitra.user.utilities.SingleLiveEvent
import com.constructionmitra.user.utilities.constants.AppConstants
import com.constructionmitra.user.utilities.constants.Role
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class JobPostViewModel @Inject constructor(
    private val repository: CMitraRepository,
    private val jobPostRequestMapper: JobPostRequestMapper
) : BaseViewModel() {

    private var _currentFragment = MutableLiveData<Int>()
    val currentFragment = _currentFragment

    private var _navigateToAddEmployeeDetails = SingleLiveEvent<Boolean>()
    val navigateToAddEmployeeDetails = _navigateToAddEmployeeDetails

    private var _newUser = SingleLiveEvent<Boolean>()
    val newUser = _newUser

    private var _profileDataWithPostedJob  = MutableLiveData<ProfileDataContractor>()
    val profileDataWithPostedJob = _profileDataWithPostedJob

    private var _tabSelected = SingleLiveEvent<Int>()
    val tabSelected = _tabSelected

    private var _navigateToReviewJob = SingleLiveEvent<Boolean>()
    val navigateToReviewJob = _navigateToReviewJob

    private var _jobRoles = MutableLiveData<List<JobRole>>()
    val jobRoles = _jobRoles

    private var _jobCategories = MutableLiveData<List<JobCategory>>()
    val jobCategories = _jobCategories

    private var _jobPosted = MutableLiveData<Boolean>()
    val jobPosted = _jobPosted

    private var _projectTypes = MutableLiveData<List<ProjectType>>()
    val projectTypes = _projectTypes

    private var _jobPostId = SingleLiveEvent<JobPostId>()
    val jobPostId = _jobPostId

    private var _selectedJobCategory: JobCategory? = null
    val selectedJobCategory
        get() = _selectedJobCategory

    private var _role: Role? = null
    val role
        get() = _role

    private var _selectedWorkList: MutableList<SelectWorkData>? = null
    val selectWorkDataList
        get() = _selectedWorkList

    private var _jobPostRequest: JobPostRequest = JobPostRequest()
    val jobPostRequest
    get() = _jobPostRequest

    private var _jobDeleted = SingleLiveEvent<Int>()
    val jobDeleted = _jobDeleted

    private var _activeLocations  = MutableLiveData<List<Location>>()
    val activeLocations = _activeLocations

    fun init(){}

    fun onFragmentSelected(position: Int) {
        _currentFragment.value = position
    }

    fun navigateToAddEmployeeDetails() {
        _navigateToAddEmployeeDetails.value = true
    }

    fun navigateToReviewYourJob() {
        _navigateToReviewJob.value = true
    }

    fun updateSelectedWorkList(list: MutableList<SelectWorkData>) {
        _selectedWorkList = list
        jobPostRequest.selectedWorkList = list
    }

    fun setJobRole(role: Role){
        _role = role
    }

    fun saveSelectedJob(jobCategory: JobCategory) {
        _selectedJobCategory = jobCategory
    }

    fun saveJobCategory(categoryId: String){
        log("categoryId is saved = $categoryId")
        _jobPostRequest.jobCategoryId = categoryId
    }

    fun saveJobRoleDetails(jobRoleDetails: JobRoleDetails) {
        log("jobRoleDetails are saved = $jobRoleDetails")
        _jobPostRequest.jobRoleDetails = jobRoleDetails
    }

    fun saveEmployeeDetails(employeeDetails: EmployeeDetails) {
        log("employee details are saved = $employeeDetails")
        _jobPostRequest.employeeDetails = employeeDetails
    }

    fun onTabSelected(position: Int) {
        _tabSelected.value = position
    }

    fun getJobRole(jobRoleId: String): String{
            return _jobRoles.value?.find { it.roleId == jobRoleId }?.jobRole ?: ""
    }

    fun getProfileWithPostedJobs(userId: String){
        viewModelScope.launch {
            when(val result = repository.fetchProfileContractor(userId)){
                is Success -> {
                    if(result.data.status == ServerConstants.STATUS_SUCCESS) {
                        _profileDataWithPostedJob.postValue(result.data.data!!)
                    }
                    else{
                        _newUser.postValue(true)
                    }
                }
                is Failure -> {
                    onFailedResponse(result.error as Exception)
                }
            }
        }
    }

    fun requestJobRoles(jobCategory: String) {
        viewModelScope.launch {
            when (val result: Result<List<JobRole>> = repository.jobRoles(jobCategory)) {
                is Success -> {
                    _jobRoles.postValue(result.data)
                }
                is Failure -> {
                    onFailedResponse(result.error as Exception)
                }
            }
        }
    }

    fun jobCategories() {
        viewModelScope.launch {
            when (val result: Result<List<JobCategory>> = repository.jobCategories()) {
                is Success -> {
                    _jobCategories.postValue(result.data)
                }
                is Failure -> {
                    onFailedResponse(result.error as Exception)
                }
            }
        }
    }

    fun postJob(userId: String, role: Role, isPublished: Boolean) {
        viewModelScope.launch {
            _jobPostRequest.isPublished = if(isPublished) 1 else 0
            val reqMap = jobPostRequestMapper.toJostPostRequest(userId, jobPostRequest = jobPostRequest)
            Timber.d("jobPostRequest -> $reqMap")
            when (val result: Result<BaseResponse<Any>> = repository.postAJob(reqMap, role)) {
                is Success -> {
                    _jobPosted.postValue(result.data.status == ServerConstants.STATUS_SUCCESS)
                }
                is Failure -> {
                    onFailedResponse(result.error as Exception)
                }
            }
        }
    }

    fun addJobWork(
        userId: String,
        jobCategoryId: String,
        jobRoleId: String,
        numOfWorker: String,
        jobPostId: String,
    ) {
        viewModelScope.launch {
            when (val result: Result<BaseResponse<JobPostId>> = repository.addJobWork(
                userId = userId,
                jobCategoryId = jobCategoryId,
                jobRoleId = jobRoleId,
                numOfWorker = numOfWorker,
                jobPostId = jobPostId
            )) {
                is Success -> {
                    _jobPostRequest?.jobPostId = result.data.data!!
                    _jobPostId.postValue(result.data.data!!)
                }
                is Failure -> {
                    onFailedResponse(result.error as Exception)
                }
            }
        }
    }

    fun deleteJobWork(
        jobWorkId: Int,
    ) {
        viewModelScope.launch {
            when (val result: Result<BaseResponse<Any>> = repository.deleteJobWork(jobWorkId = jobWorkId)) {
                is Success -> {
                    _jobDeleted.postValue(jobWorkId)
                }
                is Failure -> {
                    onFailedResponse(result.error as Exception)
                }
            }
        }
    }

    fun getProjectTypes() {
        viewModelScope.launch {
            when (val result: Result<BaseResponse<List<ProjectType>>> = repository.projectTypes()) {
                is Success -> {
                    _projectTypes.postValue(result.data.data!!)
                }
                is Failure -> {
                    onFailedResponse(result.error as Exception)
                }
            }
        }
    }

    fun getActiveLocations(){
        viewModelScope.launch {
            when(val result = repository.activeLocations()){
                is Success -> {
                    if(result.data.status == ServerConstants.STATUS_SUCCESS) {
                        _activeLocations.postValue(result.data.data!!)
                    }
                    else{
                        onFailedResponse(Exception(result.data.message))
                    }
                }
                is Failure -> {
                    onFailedResponse(result.error as Exception)
                }
            }
        }
    }

    fun clearData() {
//        _jobPostRequest = null
    }

    companion object {
        fun log(message: String){
            Timber.d("JobPostViewModel: $message")
        }
    }
}