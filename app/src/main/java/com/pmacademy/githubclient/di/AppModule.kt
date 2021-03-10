package com.pmacademy.githubclient.di

import android.content.Context
import androidx.annotation.NonNull
import com.pmacademy.githubclient.data.api.GitHubApiService
import com.pmacademy.githubclient.data.api.GitHubHeaderInterceptor
import com.pmacademy.githubclient.data.api.GitHubLoginService
import com.pmacademy.githubclient.data.api.GithubUtils.Companion.apiHost
import com.pmacademy.githubclient.data.api.GithubUtils.Companion.loginHost
import com.pmacademy.githubclient.data.api.GithubUtils.Companion.schema
import com.pmacademy.githubclient.data.pref.SharedPref
import dagger.Module
import dagger.Provides
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@ExperimentalSerializationApi
@Module
class AppModule(@NonNull private val context: Context) {

    @Singleton
    @Provides
    @NonNull
    fun context(): Context = context

    @Provides
    fun provideRetrofit(baseHost: String): Retrofit {
        return Retrofit.Builder()
            .client(
                OkHttpClient().newBuilder()
                    .addInterceptor(GitHubHeaderInterceptor(provideSharedPrefs()))
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
            )
            .baseUrl(HttpUrl.Builder().scheme(schema).host(baseHost).build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideSharedPrefs(): SharedPref = SharedPref(context())

    @Singleton
    @Provides
    fun provideLoginGithubService(): GitHubLoginService =
        provideRetrofit(loginHost).create(GitHubLoginService::class.java)

    @Singleton
    @Provides
    fun provideApiGithubService(): GitHubApiService =
        provideRetrofit(apiHost).create(GitHubApiService::class.java)
}