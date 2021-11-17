package com.constructionmitra.user.di

import com.constructionmitra.user.api.ProfileRequestsImpl
import com.constructionmitra.user.api.ProfileRequests
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProfileModule {

    @Provides
    fun bindProfileRequest(): ProfileRequests = ProfileRequestsImpl()
}