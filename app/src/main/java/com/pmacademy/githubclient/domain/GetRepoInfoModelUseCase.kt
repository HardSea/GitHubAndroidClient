package com.pmacademy.githubclient.domain

import com.pmacademy.githubclient.data.api.GithubUtils
import com.pmacademy.githubclient.tools.GithubError
import com.pmacademy.githubclient.tools.Result
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class GetRepoInfoModelUseCase {

    suspend fun invoke(
        userName: String, repoName: String, githubUtils: GithubUtils
    ): Result<RepoInfoModel, GithubError> {
        val contributorsList = githubUtils.getReposContributors(
            owner = userName,
            repo = repoName
        )
        val issueList =
            githubUtils.getReposIssues(owner = userName, repo = repoName)

        val readmeText =
            githubUtils.getRepoReadme(owner = userName, repo = repoName)

        val readmeTextReturn = if (readmeText.isError) "" else readmeText.successResult

        if (contributorsList.isError) return Result.error(contributorsList.errorResult)

        if (issueList.isError) return Result.error(issueList.errorResult)

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