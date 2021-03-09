package com.pmacademy.githubclient.di

import android.content.Context
import androidx.annotation.NonNull
import com.pmacademy.githubclient.data.api.GithubUtils
import com.pmacademy.githubclient.data.pref.SharedPref
import dagger.Module
import dagger.Provides
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Singleton

@ExperimentalSerializationApi
@Module
class AppModule(@NonNull private val context: Context) {

    @Singleton
    @Provides
    @NonNull
    fun context(): Context = context

}