package com.constructionmitra.user.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    var _errorMsg  = MutableLiveData<String>()
    val errorMsg = _errorMsg

    fun onFailedResponse(exp: Exception) {
        errorMsg.postValue(exp.toString())
    }

}