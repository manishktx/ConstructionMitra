package com.constructionmitra.user.ui.contractor.viewmodels

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
import com.constructionmitra.user.utilities.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobDetailsViewModel @Inject constructor(
    private val repository: CMitraRepository,
) : BaseViewModel() {

    private var _updateFirmDetails  = MutableLiveData<BaseResponse<Any>>()
    val updateFirmDetails = _updateFirmDetails

    private var _updateJobRoles  = MutableLiveData<BaseResponse<Any>>()
    val updateJobRoles = _updateJobRoles

    private var _currentFragment = MutableLiveData<Int>()
    val currentFragment = _currentFragment

    private var _navigateToAddEmployeeDetails = SingleLiveEvent<Boolean>()
    val navigateToAddEmployeeDetails = _navigateToAddEmployeeDetails

    private var _navigateToReviewJob = SingleLiveEvent<Boolean>()
    val navigateToReviewJob = _navigateToReviewJob

    fun onFragmentSelected(position: Int){
        _currentFragment.value = position
    }

    fun navigateToAddEmployeeDetails(){
        _navigateToAddEmployeeDetails.value = true
    }

    fun navigateToReviewYourJob(){
        _navigateToReviewJob.value = true
    }

    fun requestOtp(mobile: String) {
        viewModelScope.launch {
            when (val result: Result<LoginResponse> = repository.requestOtp(mobile)){
                is Success -> {
//                    loginResponse.postValue(result.data)
                }
                is Failure -> {
                    onFailedResponse(result.error as Exception)
                }
            }
        }
    }
}