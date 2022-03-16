package com.constructionmitra.user.ui.contractor.viewmodels

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.constructionmitra.user.api.Failure
import com.constructionmitra.user.api.Success
import com.constructionmitra.user.data.PostedJob
import com.constructionmitra.user.data.ProfileData
import com.constructionmitra.user.data.ProfileDataContractor
import com.constructionmitra.user.repository.CMitraRepository
import com.constructionmitra.user.ui.base.BaseViewModel
import com.constructionmitra.user.utilities.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ContractorProfileViewModel @Inject constructor(
    private val repository: CMitraRepository,
) : BaseViewModel() {

    private var _profileData  = MutableLiveData<ProfileData>()
    val profileData = _profileData

    private var _galleryImageSaved = SingleLiveEvent<File>()
    val galleryImageSaved = _galleryImageSaved

    private var _profileUpdated = SingleLiveEvent<ProfileData>()
    val profileUpdated = _profileUpdated

    private var _jobDeleted = SingleLiveEvent<PostedJob>()
    val jobDeleted = _jobDeleted

    private var _jobPublished = SingleLiveEvent<PostedJob>()
    val jobPublished = _jobPublished

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

    fun decodeAndSaveGalleryImage(imageUri: Uri, bitmapConfig: BitmapConfig, context: Context) {
        viewModelScope.launch {
            val bitmap = BitmapUtils.decodeStreamToBitmap(context, imageUri, bitmapConfig)
            withContext(Dispatchers.IO) {
                bitmap?.let {
                    val outputFile = FileUtils.saveBitmapToFile(bitmap, context)
                    _galleryImageSaved.postValue(outputFile)
                }
            }
        }
    }

    fun updateProfile(hashMap: HashMap<String, String>){
        viewModelScope.launch {
            when(val result = repository.updateProfile(hashMap)){
                is Success -> {
                    if(result.data.status == ServerConstants.STATUS_SUCCESS) {
                        _profileUpdated.postValue(result.data.data!!)
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

    fun updateJobStatus(
        postedJob: PostedJob,
        userId: String,
        type: String,
        isToPublished: Boolean = false
    ){
        viewModelScope.launch {
            when(val result = repository.updateJobStatus(
                postedJob.categoryId!!, userId, postedJob.jobRoleId, postedJob.jobPostId, type )
            ){
                is Success -> {
                    if(result.data.status == ServerConstants.STATUS_SUCCESS) {
                        if(isToPublished)
                            _jobPublished.postValue(postedJob)
                        else
                            _jobDeleted.postValue(postedJob)
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