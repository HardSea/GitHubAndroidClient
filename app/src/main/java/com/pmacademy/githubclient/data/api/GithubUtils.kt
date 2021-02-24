package com.pmacademy.githubclient.data.api

import android.net.Uri
import android.util.Log
import com.google.gson.JsonParser
import com.pmacademy.githubclient.data.model.*
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


@ExperimentalSerializationApi
class GithubUtils {

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
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
            )
            .baseUrl(HttpUrl.Builder().scheme(schema).host(baseHost).build())
            .addConverterFactory(GsonConverterFactory.create())
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

    suspend fun getUserReposList(
        username: String,
        authToken: String
    ): Result<List<ReposResponse>, GithubError> {
        return try {
            Result.success(apiGithubService.getListUserRepos(username = username, auth = authToken))
        } catch (e: Exception) {
            githubInterceptor.getError(e)
        }
    }

    suspend fun getIssueCommentsList(
        issueNumber: Int,
        owner: String,
        repo: String,
        authToken: String
    ): Result<List<IssueCommentResponse>, GithubError> {
        return try {
            Result.success(
                apiGithubService.getIssueCommentsList(
                    issueNumber = issueNumber,
                    owner = owner,
                    repo = repo,
                    auth = authToken
                )
            )
        } catch (e: Exception) {
            githubInterceptor.getError(e)
        }
    }

    suspend fun getRepoReadme(
        owner: String,
        repo: String,
        authToken: String
    ): Result<String, GithubError> {
        return try {
            val readme = apiGithubService.getRepoReadme(
                owner = owner,
                repo = repo,
                auth = authToken
            )
            val encodeReadme = StringDecoder().decodeText(readme.content, readme.encoding)
            return Result.success(encodeReadme)
        } catch (e: Exception) {
            githubInterceptor.getError(e)
        }
    }

    suspend fun getReposContributors(
        owner: String,
        repo: String,
        authToken: String
    ): Result<List<UserResponse>, GithubError> {
        return try {
            Result.success(
                apiGithubService.getReposContributors(
                    owner = owner,
                    repo = repo,
                    auth = authToken
                )
            )
        } catch (e: Exception) {
            Log.d("tagsss", "getReposContributors: $e")
            githubInterceptor.getError(e)
        }
    }

    suspend fun getReposIssues(
        owner: String,
        repo: String,
        authToken: String
    ): Result<List<IssueResponse>, GithubError> {
        return try {
            Result.success(
                apiGithubService.getReposIssues(
                    owner = owner,
                    repo = repo,
                    auth = authToken
                )
            )
        } catch (e: Exception) {
            githubInterceptor.getError(e)
        }
    }

    suspend fun createReactionForIssueComment(
        owner: String,
        repo: String,
        authToken: String,
        commentId: Int,
        clickType: ReactionType
    ): Result<Boolean, GithubError> {
        return try {
            val reactionString = when (clickType) {
                ReactionType.LIKE -> "+1"
                ReactionType.DISLIKE -> "-1"
                ReactionType.LAUGH -> "laugh"
                ReactionType.CONFUSED -> "confused"
                ReactionType.HEART -> "heart"
                ReactionType.HOORAY -> "hooray"
                ReactionType.ROCKET -> "rocket"
                ReactionType.EYES -> "eyes"
            }

            apiGithubService.createReactionForIssueComment(
                owner = owner,
                repo = repo,
                auth = authToken,
                commentId = commentId,
                reaction = JsonParser().parse("{\"content\": \"$reactionString\"}").asJsonObject
            )
            Result.success(true)
        } catch (e: Exception) {
            githubInterceptor.getError(e)
        }
    }

    /// usersSearch
    suspend fun getUsersSearch(
       userName: String,
       authToken: String
    ): Result<List<UserResponse>, GithubError> {
        return try {
            Result.success(apiGithubService.getUsersSearch(userName = userName, auth = authToken))
        } catch (e: Exception) {
            githubInterceptor.getError(e)
        }
    }





}