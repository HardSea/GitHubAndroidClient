package com.pmacademy.githubclient.data.api

import com.pmacademy.githubclient.data.model.IssueCommentResponse
import com.pmacademy.githubclient.data.model.IssueResponse
import com.pmacademy.githubclient.data.model.ReactionType
import com.pmacademy.githubclient.data.model.UserResponse
import com.pmacademy.githubclient.tools.GithubError
import com.pmacademy.githubclient.tools.Result
import com.pmacademy.githubclient.tools.StringDecoder
import com.pmacademy.myapplicationtemp.data.ReposResponse
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject


@ExperimentalSerializationApi
class GithubUtils @Inject constructor(
    private val apiGithubService: GitHubApiService,
    private val githubInterceptor: GitHubInterceptor
) {

    companion object {
        const val clientId = "4c730b7299ec5bf137ae"
        const val clientSecret = "27893f6c73800e60a2b0ad05f388335824f88811"
        const val redirectUrl = "androidgithubclient://callback"
        const val scopes = "repo user admin workflow"
        const val schema = "https"
        const val loginHost = "github.com"
        const val apiHost = "api.github.com"
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