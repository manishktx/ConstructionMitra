package com.constructionmitra.user.ui.contractor.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.constructionmitra.user.api.Failure
import com.constructionmitra.user.api.JobPostRequestMapper
import com.constructionmitra.user.api.Result
import com.constructionmitra.user.api.Success
import com.constructionmitra.user.data.*
import com.constructionmitra.user.repository.CMitraRepository
import com.constructionmitra.user.ui.base.BaseViewModel
import com.constructionmitra.user.utilities.ServerConstants
import com.constructionmitra.user.utilities.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class ContractorProfileViewModel (
) : BaseViewModel() {

    private var _showTabs = SingleLiveEvent<Boolean>()
    val showTabs = _showTabs

    private var _initJobPost = SingleLiveEvent<Boolean>()
    val initJobPost = _initJobPost

    fun init(){}

    fun showHomeTabs(value: Boolean)
    {
        _showTabs.value = value
    }

    fun initJobPostProcess(value: Boolean)
    {
        _initJobPost.value = value
    }
}