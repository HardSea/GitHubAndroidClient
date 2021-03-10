package com.pmacademy.githubclient.data.api

import android.net.Uri
import com.pmacademy.githubclient.data.model.*
import com.pmacademy.githubclient.data.pref.SharedPref
import com.pmacademy.githubclient.tools.GithubError
import com.pmacademy.githubclient.tools.Result
import com.pmacademy.githubclient.tools.StringDecoder
import com.pmacademy.myapplicationtemp.data.ReposResponse
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


@ExperimentalSerializationApi
class GithubUtils @Inject constructor(private val sharedPrefs: SharedPref) {

    private companion object {
        const val clientId = "4c730b7299ec5bf137ae"
        const val clientSecret = "27893f6c73800e60a2b0ad05f388335824f88811"
        const val redirectUrl = "androidgithubclient://callback"
        const val scopes = "repo user admin workflow"
        const val schema = "https"
        const val loginHost = "github.com"
        const val apiHost = "api.github.com"
    }

    private val githubInterceptor = GitHubInterceptor()

    private fun createRetrofit(baseHost: String): Retrofit {
        return Retrofit.Builder()
            .client(
                OkHttpClient().newBuilder()
                    .addInterceptor(GitHubHeaderInterceptor(sharedPrefs.token))
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
            )
            .baseUrl(HttpUrl.Builder().scheme(schema).host(baseHost).build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val loginGithubService: GitHubLoginService =
        createRetrofit(loginHost).create(GitHubLoginService::class.java)


    private val apiGithubService: GitHubApiService =
        createRetrofit(apiHost).create(GitHubApiService::class.java)


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

    suspend fun getUser(): Result<UserResponse, GithubError> {
        return try {
            Result.success(apiGithubService.getUser())
        } catch (e: Exception) {
            githubInterceptor.getError(e)
        }
    }

    suspend fun getUserReposList(
        username: String
    ): Result<List<ReposResponse>, GithubError> {
        return try {
            Result.success(apiGithubService.getListUserRepos(username = username))
        } catch (e: Exception) {
            githubInterceptor.getError(e)
        }
    }

    suspend fun getIssueCommentsList(
        issueNumber: Int,
        owner: String,
        repo: String
    ): Result<List<IssueCommentResponse>, GithubError> {
        return try {
            Result.success(
                apiGithubService.getIssueCommentsList(
                    issueNumber = issueNumber,
                    owner = owner,
                    repo = repo
                )
            )
        } catch (e: Exception) {
            githubInterceptor.getError(e)
        }
    }

    suspend fun getRepoReadme(
        owner: String,
        repo: String
    ): Result<String, GithubError> {
        return try {
            val readme = apiGithubService.getRepoReadme(
                owner = owner,
                repo = repo
            )
            if (readme.content != null && readme.encoding != null) {
                val encodeReadme = StringDecoder().decodeText(readme.content, readme.encoding)
                return Result.success(encodeReadme)
            } else {
                Result.success("")
            }
        } catch (e: Exception) {
            githubInterceptor.getError(e)
        }
    }

    suspend fun getReposContributors(
        owner: String,
        repo: String
    ): Result<List<UserResponse>, GithubError> {
        return try {
            Result.success(
                apiGithubService.getRepoContributors(
                    owner = owner,
                    repo = repo
                )
            )
        } catch (e: Exception) {
            githubInterceptor.getError(e)
        }
    }

    suspend fun getReposIssues(
        owner: String,
        repo: String
    ): Result<List<IssueResponse>, GithubError> {
        return try {
            Result.success(
                apiGithubService.getRepoIssues(
                    owner = owner,
                    repo = repo
                )
            )
        } catch (e: Exception) {
            githubInterceptor.getError(e)
        }
    }

    suspend fun createReactionForIssueComment(
        owner: String,
        repo: String,
        commentId: Int,
        clickType: ReactionType
    ): Result<Boolean, GithubError> {
        return try {
            apiGithubService.createReactionForIssueComment(
                owner = owner,
                repo = repo,
                commentId = commentId,
                reaction = Reaction(clickType.stringReaction)
            )
            Result.success(true)
        } catch (e: Exception) {
            githubInterceptor.getError(e)
        }
    }

    suspend fun getUsersSearch(
        userName: String
    ): Result<List<UserResponse>, GithubError> {
        return try {
            val userList = apiGithubService.getUsersSearch(q = userName).usersList
            if (userList != null) {
                Result.success(userList)
            } else {
                Result.success(listOf())
            }
        } catch (e: Exception) {
            githubInterceptor.getError(e)
        }
    }
}