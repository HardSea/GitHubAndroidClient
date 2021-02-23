package com.pmacademy.githubclient.domain

import com.pmacademy.githubclient.data.model.IssueResponse
import com.pmacademy.githubclient.data.model.UserResponse

data class RepoInfoModel(
    val repoTitle: String,
    val readmeText: String,
    val contributorsList: List<UserResponse>,
    val issuesList: List<IssueResponse>
)
