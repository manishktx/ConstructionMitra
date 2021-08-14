package com.constructionmitra.user.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.constructionmitra.user.api.Failure
import com.constructionmitra.user.api.Success
import com.constructionmitra.user.data.Job
import com.constructionmitra.user.repository.CMitraRepository
import com.constructionmitra.user.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AvailableJobsViewModel @Inject constructor(
    private val repository: CMitraRepository
) : BaseViewModel() {
    private var _availableJobs  = MutableLiveData<List<Job>>()
    val availableJobs = _availableJobs

    private var _appliedJobs  = MutableLiveData<List<Job>>()
    val appliedJobs = _appliedJobs

    fun getAvailableJobs(
        userId: String,
        limit: Int,
        offset: Int,
        locationId: String,
        isGetAvailableJobs: Boolean
    ){
        viewModelScope.launch {
            when(val result = repository.getAvailableJobs(
                userId, limit, offset, locationId, isGetAvailableJobs
            )){
                is Success -> {
                    if(isGetAvailableJobs)
                        _availableJobs.postValue(result.data.data!!)
                    else
                        _appliedJobs.postValue(result.data.data!!)
                }
                is Failure -> {
                    onFailedResponse(Exception(result.error))
                }
            }
        }
    }
}