package com.constructionmitra.user.di

import android.content.Context
import android.content.SharedPreferences
import com.constructionmitra.user.data.AppPreferences
import com.constructionmitra.user.utilities.constants.UserType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PreferencesModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences{
        return context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }
}