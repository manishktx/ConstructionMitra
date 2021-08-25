package com.constructionmitra.user.ui.contractor.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.constructionmitra.user.api.Failure
import com.constructionmitra.user.api.Success
import com.constructionmitra.user.data.ProfileData
import com.constructionmitra.user.data.ProfileDataContractor
import com.constructionmitra.user.repository.CMitraRepository
import com.constructionmitra.user.ui.base.BaseViewModel
import com.constructionmitra.user.utilities.ServerConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContractorProfileViewModel @Inject constructor(
    private val repository: CMitraRepository,
) : BaseViewModel() {

    private var _profileData  = MutableLiveData<ProfileData>()
    val profileData = _profileData

    private var _profileDataWithPostedJob  = MutableLiveData<ProfileDataContractor>()
    val profileDataWithPostedJob = _profileDataWithPostedJob

    fun fetchProfileInfo(userId: String, token: String){
        viewModelScope.launch {
            when(val result = repository.fetchProfile(userId, token)){
                is Success -> {
                    if(result.data.status == ServerConstants.STATUS_SUCCESS) {
                        _profileData.postValue(result.data.data!!)
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

    fun getProfileWithPostedJobs(userId: String){
        viewModelScope.launch {
            when(val result = repository.fetchProfileContractor(userId)){
                is Success -> {
                    if(result.data.status == ServerConstants.STATUS_SUCCESS) {
                        _profileDataWithPostedJob.postValue(result.data.data!!)
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

}