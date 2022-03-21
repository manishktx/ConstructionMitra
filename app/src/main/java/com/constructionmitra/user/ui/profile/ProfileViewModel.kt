package com.constructionmitra.user.ui.profile

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.constructionmitra.user.api.Failure
import com.constructionmitra.user.api.ProfileRequests
import com.constructionmitra.user.api.Success
import com.constructionmitra.user.data.Location
import com.constructionmitra.user.data.ProfileData
import com.constructionmitra.user.data.WorkExperience
import com.constructionmitra.user.data.WorkHistory
import com.constructionmitra.user.repository.CMitraRepository
import com.constructionmitra.user.ui.base.BaseViewModel
import com.constructionmitra.user.utilities.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
   private val repository: CMitraRepository,
   private val profileRequests: ProfileRequests
): BaseViewModel() {

    private val defaultDispatcher = Dispatchers.IO

    private var _profileData  = MutableLiveData<ProfileData>()
    val profileData = _profileData

    private var _activeLocations  = MutableLiveData<List<Location>>()
    val activeLocations = _activeLocations

    private var _workExpOptions  = MutableLiveData<List<WorkExperience>>()
    val workExpOptions = _workExpOptions

    private var _profileUpdated  = SingleLiveEvent<Boolean>()
    val profileUpdated = _profileUpdated

    private var _profileDataUpdated  = SingleLiveEvent<ProfileData>()
    val profileDataUpdated = _profileDataUpdated

    private var _workSampleAdded  = SingleLiveEvent<Boolean>()
    val workSampleAdded = _workSampleAdded

    private var _letterHeadUpdated  = SingleLiveEvent<Boolean>()
    val letterHeadUpdated = _letterHeadUpdated

    private var _workHistory  = SingleLiveEvent<List<WorkHistory>>()
    val workHistory = _workHistory

    private var _profilePictureUpdated  = SingleLiveEvent<Boolean>()
    val profilePictureUpdated = _profilePictureUpdated


    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }

    private var _galleryImageSaved = SingleLiveEvent<File>()
    val galleryImageSaved = _galleryImageSaved

    val text: LiveData<String> = _text

    fun decodeAndSaveGalleryImage(imageUri: Uri, bitmapConfig: BitmapConfig, context: Context) {
        viewModelScope.launch {
            val bitmap = BitmapUtils.decodeStreamToBitmap(context, imageUri, bitmapConfig)
            withContext(defaultDispatcher) {
                bitmap?.let {
                    val outputFile = FileUtils.saveBitmapToFile(bitmap, context)
                    _galleryImageSaved.postValue(outputFile)
                }
            }
        }
    }

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

    fun updateProfileData(hashMap: HashMap<String, String>){
        viewModelScope.launch {
            when(val result = repository.updateProfile(hashMap)){
                is Success -> {
                    if(result.data.status == ServerConstants.STATUS_SUCCESS) {
                        _profileDataUpdated.postValue(result.data.data!!)
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


    fun addWork(userId: String, file: File){
        viewModelScope.launch {
            when(val result =  repository.addWork(userId.toInt(), createMultipartBody(file))){
                is Success -> {
                    _workSampleAdded.postValue(result.data.status == ServerConstants.STATUS_SUCCESS)
                }
                is Failure -> {
                    onFailedResponse(result.error as Exception)
                }
            }
        }
    }

    fun updateProfilePicture(userId: String, token: String, file: File){
        viewModelScope.launch {
            when(val result =  repository.updateProfilePic(userId.toInt(), token, createMultipartBodyForProfilePic(file))){
                is Success -> {
                    _workSampleAdded.postValue(result.data.status == ServerConstants.STATUS_SUCCESS)
                }
                is Failure -> {
                    onFailedResponse(result.error as Exception)
                }
            }
        }
    }

    fun updateLetterHead(userId: String, token: String, file: File){
        viewModelScope.launch {
            when(val result =  repository.updateLetterHead(userId.toInt(), token, createMultipartBodyForLetterHead(file))){
                is Success -> {
                    _letterHeadUpdated.postValue(result.data.status == ServerConstants.STATUS_SUCCESS)
                }
                is Failure -> {
                    onFailedResponse(result.error as Exception)
                }
            }
        }
    }


    fun workHistory(userId: String){
        viewModelScope.launch {
            when(val result =  repository.workHistory(userId)){
                is Success -> {
                    _workHistory.postValue(result.data.data!!)
                }
                is Failure -> {
                    onFailedResponse(result.error as Exception)
                }
            }
        }
    }

    private fun createMultipartBody(file: File): MultipartBody.Part {
        return MultipartBody.Part.createFormData(
            ProfileRequests.PARAM_IMAGE, file.name,
            getRequestBody(file, MIMEType.IMAGE.value)
        )
    }


    private fun createMultipartBodyForProfilePic(file: File): MultipartBody.Part {
        return MultipartBody.Part.createFormData(
            ProfileRequests.PROFILE_PIC, file.name,
            getRequestBody(file, MIMEType.IMAGE.value)
        )
    }

    private fun createMultipartBodyForLetterHead(file: File): MultipartBody.Part {
        return MultipartBody.Part.createFormData(
            ProfileRequests.LETTER_HEAD, file.name,
            getRequestBody(file, MIMEType.IMAGE.value)
        )
    }

    private fun getRequestBody(file: File, mimeType: String ) =
        file.asRequestBody(mimeType.toMediaTypeOrNull())

}