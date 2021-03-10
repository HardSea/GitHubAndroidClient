package com.pmacademy.githubclient.data.api

import com.pmacademy.githubclient.data.pref.SharedPref
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class GitHubHeaderInterceptor @Inject constructor(private val sharedPref: SharedPref) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", sharedPref.token)
            .addHeader("Accept", "application/vnd.github.v3+json")
            .build()
        return chain.proceed(request)
    }
}