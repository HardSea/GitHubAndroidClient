package com.pmacademy.githubclient.tools

import android.net.Uri
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.pmacademy.githubclient.data.api.GitHubServiceApi
import com.pmacademy.githubclient.data.model.AccessToken
import com.pmacademy.myapplicationtemp.data.Repos
import com.pmacademy.myapplicationtemp.data.User
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit

@ExperimentalSerializationApi
class GithubUtils {

    private companion object {
        const val clientId = "Iv1.2a4082c0aa5cda51"
        const val clientSecret = "69b0c483062b458caa835bb584b9878faf38a2e2"
        const val redirectUrl = "androidgithubclient://callback"
        const val scopes = "repo, user"
        const val schema = "https"
        const val loginHost = "github.com"
        const val apiHost = "api.github.com"
    }

    private val converterFactory: Converter.Factory by lazy {
        Json { ignoreUnknownKeys = true }
            .asConverterFactory("application/json".toMediaType())
    }

    private fun createRetrofit(baseHost: String): Retrofit {
        return Retrofit.Builder()
            .client(
                OkHttpClient().newBuilder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
            )
            .baseUrl(HttpUrl.Builder().scheme(schema).host(baseHost).build())
            .addConverterFactory(converterFactory)
            .build()
    }

    private val loginGithubService: GitHubServiceApi by lazy {
        createRetrofit(loginHost).create(GitHubServiceApi::class.java)
    }

    private val apiGithubService: GitHubServiceApi by lazy {
        createRetrofit(apiHost).create(GitHubServiceApi::class.java)
    }

    fun buildAuthGitHubUrl(): Uri {
        return Uri.Builder()
            .scheme(schema)
            .authority(loginHost)
            .appendEncodedPath("login/oauth/authorize")
            .appendQueryParameter("client_id", clientId)
            .appendQueryParameter("scope", scopes)
            .appendQueryParameter("redirect_url", redirectUrl)
            .build()
    }

    fun getCodeFromUri(uri: Uri?): String? {
        uri ?: return null
        if (!uri.toString().startsWith(redirectUrl)) {
            return null
        }
        return uri.getQueryParameter("code")
    }

    suspend fun getAccessToken(code: String): AccessToken {
        return loginGithubService.getAccessToken(clientId, clientSecret, code)
    }

    suspend fun getUser(token: String): User {
        return apiGithubService.getUser(token)
    }

    suspend fun getUserRepos(token: String): List<Repos> {
        return apiGithubService.getUserRepos(token)
    }

}