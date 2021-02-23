package com.pmacademy.githubclient.data.api

import android.net.Uri
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.pmacademy.githubclient.data.model.AccessTokenResponse
import com.pmacademy.githubclient.data.model.IssueResponse
import com.pmacademy.githubclient.data.model.UserResponse
import com.pmacademy.githubclient.tools.GithubError
import com.pmacademy.githubclient.tools.Result
import com.pmacademy.myapplicationtemp.data.ReposResponse
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

    private val githubInterceptor = GitHubInterceptor()

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

    suspend fun getAccessToken(code: String): AccessTokenResponse {
        return loginGithubService.getAccessToken(clientId, clientSecret, code)
    }

    suspend fun getUser(token: String): Result<UserResponse, GithubError> {
        return try {
            Result.success(apiGithubService.getUser(token))
        } catch (e: Exception) {
            githubInterceptor.getError(e)
        }
    }

    suspend fun getListUserRepos(username: String): Result<List<ReposResponse>, GithubError> {
        return try {
            Result.success(apiGithubService.getListUserRepos(username))
        } catch (e: Exception) {
            githubInterceptor.getError(e)
        }
    }

    suspend fun getReposContributors(
        owner: String,
        repo: String
    ): Result<List<UserResponse>, GithubError> {
        return try {
            Result.success(apiGithubService.getReposContributors(owner = owner, repo = repo))
        } catch (e: Exception) {
            githubInterceptor.getError(e)
        }
    }

    suspend fun getReposIssues(
        owner: String,
        repo: String
    ): Result<List<IssueResponse>, GithubError> {
        return try {
            Result.success(apiGithubService.getReposIssues(owner = owner, repo = repo))
        } catch (e: Exception) {
            githubInterceptor.getError(e)
        }
    }

}