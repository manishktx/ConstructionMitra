package com.constructionmitra.user.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.constructionmitra.user.api.Failure
import com.constructionmitra.user.api.Result
import com.constructionmitra.user.api.Success
import com.constructionmitra.user.data.BaseResponse
import com.constructionmitra.user.data.JobRole
import com.constructionmitra.user.data.LoginResponse
import com.constructionmitra.user.data.VerifyOtpData
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

    fun requestOtp(mobile: String) {
        viewModelScope.launch {
            when (val result: Result<LoginResponse> = repository.requestOtp(mobile)){
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
                        verifyOtpData.postValue(result.data.data)
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

    fun requestJobRoles() {
        viewModelScope.launch {
            when (val result: Result<List<JobRole>> = repository.jobRoles()){
                is Success -> {
                    jobRoles.postValue(result.data)
                }
                is Failure -> {
                    onFailedResponse(result.error as Exception)
                }
            }
        }
    }
}