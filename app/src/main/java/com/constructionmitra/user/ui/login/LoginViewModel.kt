package com.constructionmitra.user.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.constructionmitra.user.api.Failure
import com.constructionmitra.user.api.Result
import com.constructionmitra.user.api.Success
import com.constructionmitra.user.data.*
import com.constructionmitra.user.repository.CMitraRepository
import com.constructionmitra.user.ui.base.BaseViewModel
import com.constructionmitra.user.utilities.ServerConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: CMitraRepository,
) : BaseViewModel() {

    private var _loginResponse  = MutableLiveData<LoginResponse>()
    val loginResponse = _loginResponse

    private var _verifyOtpData  = MutableLiveData<VerifyOtpData>()
    val verifyOtpData = _verifyOtpData

    private var _jobRoles  = MutableLiveData<List<JobRole>>()
    val jobRoles = _jobRoles

    private var _updateFirmDetails  = MutableLiveData<BaseResponse<Any>>()
    val updateFirmDetails = _updateFirmDetails

    private var _updateJobRoles  = MutableLiveData<BaseResponse<Any>>()
    val updateJobRoles = _updateJobRoles

    private var _jobCategories = MutableLiveData<List<JobCategory>>()
    val jobCategories = _jobCategories

    fun requestOtp(mobile: String, jobRole: String, name: String) {
        viewModelScope.launch {
            when (val result: Result<LoginResponse> = repository.requestOtp(mobile, jobRole, name)){
                is Success -> {
                    loginResponse.postValue(result.data)
                }
                is Failure -> {
                    onFailedResponse(result.error as Exception)
                }
            }
        }
    }


    fun verifyOtp(mobile: String, otp: String) {
        viewModelScope.launch {
            when (val result: Result<BaseResponse<VerifyOtpData>> = repository.verifyOtp(mobile, otp)){
                is Success -> {
                    if(result.data.status.equals(ServerConstants.STATUS_SUCCESS, ignoreCase = true)){
                        _verifyOtpData.postValue(result.data.data!!)
                    }
                    else{
                        onFailedResponse(java.lang.Exception(result.data.message))
                    }
                }
                is Failure -> {
                    onFailedResponse(result.error as Exception)
                }
            }
        }
    }
    fun getCheckedItemsIds(jobRoles: List<JobRole>): String{
        val idList = jobRoles.map {
            jobRole ->  jobRole.roleId
        }
        return idList.joinToString(separator = ",")
    }

    fun requestJobRoles(jobCategory: String) {
        viewModelScope.launch {
            when (val result: Result<List<JobRole>> = repository.jobRoles(jobCategory)){
                is Success -> {
                    jobRoles.postValue(result.data)
                }
                is Failure -> {
                    onFailedResponse(result.error as Exception)
                }
            }
        }
    }

    fun updateFirmDetails(userId: String, firmName: String, numOfWorkers: String, token: String){
        viewModelScope.launch {
            when (val result: Result<BaseResponse<Any>> = repository.updateProfile(
                hashMapOf(
                    "user_id" to userId,
                    "firm_name" to firmName,
                    "no_of_workers" to numOfWorkers,
                    "token" to token,
                )
            )){
                is Success -> {
                    if(result.data.status.equals(ServerConstants.STATUS_SUCCESS, ignoreCase = true)){
                        _updateFirmDetails.postValue(result.data)
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

    fun updateJobRoles(userId: String, token: String, jobRoleIds: String){
        viewModelScope.launch {
            when(val result = repository.updateJobRoles(userId, token, jobRoleIds)){
                is Success ->
                    if(result.data.status.equals(ServerConstants.STATUS_SUCCESS, ignoreCase = true)){
                        _updateJobRoles.postValue(result.data)
                    }
                    else{
                        onFailedResponse(Exception(result.data.message))
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
}