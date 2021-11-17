package com.constructionmitra.user.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.constructionmitra.user.api.Failure
import com.constructionmitra.user.api.Success
import com.constructionmitra.user.data.ProfileData
import com.constructionmitra.user.repository.CMitraRepository
import com.constructionmitra.user.ui.base.BaseViewModel
import com.constructionmitra.user.utilities.ServerConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
   private val repository: CMitraRepository
): BaseViewModel() {

    private var _profileData  = MutableLiveData<ProfileData>()
    val profileData = _profileData

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

                }
            }
        }
    }

    fun updateProfile(hashMap: HashMap<String, String>){
        viewModelScope.launch {
            when(val result = repository.updateProfile(hashMap)){
                is Success -> {
                    if(result.data.status == ServerConstants.STATUS_SUCCESS) {

                    }
                    else{
                        onFailedResponse(Exception(result.data.message))
                    }
                }
                is Failure -> {

                }
            }
        }
    }
}