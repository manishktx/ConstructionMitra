package com.constructionmitra.user.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.constructionmitra.user.api.Failure
import com.constructionmitra.user.api.ProfileRequests
import com.constructionmitra.user.api.Success
import com.constructionmitra.user.data.Location
import com.constructionmitra.user.data.ProfileData
import com.constructionmitra.user.data.WorkExperience
import com.constructionmitra.user.repository.CMitraRepository
import com.constructionmitra.user.ui.base.BaseViewModel
import com.constructionmitra.user.utilities.ServerConstants
import com.constructionmitra.user.utilities.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
   private val repository: CMitraRepository,
   private val profileRequests: ProfileRequests
): BaseViewModel() {

    private var _profileData  = MutableLiveData<ProfileData>()
    val profileData = _profileData

    private var _activeLocations  = MutableLiveData<List<Location>>()
    val activeLocations = _activeLocations

    private var _workExpOptions  = MutableLiveData<List<WorkExperience>>()
    val workExpOptions = _workExpOptions

    private var _profileUpdated  = SingleLiveEvent<Boolean>()
    val profileUpdated = _profileUpdated

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

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

    fun updateAddress(token: String, userId: String, address: String?, permanentAddress: String?){
        if(address.isNullOrEmpty() && permanentAddress.isNullOrEmpty()){
            // not a valid request
            return
        }
        viewModelScope.launch {
            when(val result = repository.updateProfile(
                profileRequests.updateAddress(
                    token,
                    userId,
                    address,
                    permanentAddress))
            ){
                is Success -> {
                    if(result.data.status == ServerConstants.STATUS_SUCCESS) {
                        _profileUpdated.postValue(true)
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

    fun getWorkExpOptions(){
        viewModelScope.launch {
            when(val result = repository.workExpOptions()){
                is Success -> {
                    if(result.data.status == ServerConstants.STATUS_SUCCESS) {
                        _workExpOptions.postValue(result.data.data!!)
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


    fun updateProfile(hashMap: HashMap<String, String>){
        viewModelScope.launch {
            when(val result = repository.updateProfile(hashMap)){
                is Success -> {
                    if(result.data.status == ServerConstants.STATUS_SUCCESS) {
                        _profileUpdated.postValue(true)
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