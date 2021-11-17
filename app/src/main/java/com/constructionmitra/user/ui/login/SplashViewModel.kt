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
class SplashViewModel @Inject constructor(
    private val repository: CMitraRepository,
) : BaseViewModel() {

    private var _configData  = MutableLiveData<ConfigData>()
    val configData = _configData

    fun getAppConfig() {
        viewModelScope.launch {
            when (val result: Result<BaseResponse<ConfigData>> = repository.appConfig()){
                is Success -> {
                    _configData.postValue(result.data.data!!)
                }
                is Failure -> {
                    onFailedResponse(result.error as Exception)
                }
            }
        }
    }
}