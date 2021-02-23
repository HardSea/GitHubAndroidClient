package com.pmacademy.githubclient.domain

import android.util.Log
import com.pmacademy.githubclient.data.api.GithubUtils
import com.pmacademy.githubclient.tools.GithubError
import com.pmacademy.githubclient.tools.Result
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class GetRepoInfoModelUseCase {

    private val githubUtils: GithubUtils by lazy { GithubUtils() }

    suspend fun invoke(
        userName: String,
        repoName: String,
        authToken: String
    ): Result<RepoInfoModel, GithubError> {
        val contributorsList = githubUtils.getReposContributors(
            owner = userName,
            repo = repoName,
            authToken = authToken
        )
        val issueList =
            githubUtils.getReposIssues(owner = userName, repo = repoName, authToken = authToken)

        val readmeText =
            githubUtils.getRepoReadme(owner = userName, repo = repoName, authToken = authToken)

        val readmeTextReturn = if (readmeText.isError) "" else readmeText.successResult

        Log.d("TAG555", "invoke: $readmeTextReturn")

        if (contributorsList.isError)
            if (contributorsList.errorResult == GithubError.UNAUTHORIZED)
                return Result.error(contributorsList.errorResult)

        if (readmeText.isError)
            if (readmeText.errorResult == GithubError.UNAUTHORIZED)
                return Result.error(readmeText.errorResult)

        if (readmeText.isError)
            if (readmeText.errorResult == GithubError.UNAUTHORIZED)
                return Result.error(readmeText.errorResult)

        return Result.success(
            RepoInfoModel(
                repoTitle = repoName,
                readmeText = readmeTextReturn,
                contributorsList = contributorsList.successResult,
                issuesList = issueList.successResult
            )
        )
    }
}