package com.constructionmitra.user.ui.work

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.constructionmitra.user.api.Failure
import com.constructionmitra.user.api.Success
import com.constructionmitra.user.data.BaseResponse
import com.constructionmitra.user.data.Job
import com.constructionmitra.user.data.LoginResponse
import com.constructionmitra.user.repository.CMitraRepository
import com.constructionmitra.user.ui.base.BaseViewModel
import com.constructionmitra.user.utilities.ServerConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkDetailViewModel @Inject constructor(
    private val repository: CMitraRepository
) : BaseViewModel() {
    private var _requestForWork  = MutableLiveData<Boolean>()
    val requestForWork = _requestForWork

    fun mapJob(
       userId: String,
       jobId: String
    ){
        viewModelScope.launch {
            when(val result = repository.mapJob(
                userId = userId, jobId = jobId
            )){
                is Success -> {
                    _requestForWork.postValue(result.data.status.equals(ServerConstants.STATUS_SUCCESS, ignoreCase = true))
                }
                is Failure -> {
                    onFailedResponse(Exception(result.error))
                }
            }
        }
    }
}