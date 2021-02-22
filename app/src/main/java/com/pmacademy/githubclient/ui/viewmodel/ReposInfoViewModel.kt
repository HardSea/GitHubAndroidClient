package com.pmacademy.githubclient.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmacademy.githubclient.data.api.GithubUtils
import com.pmacademy.githubclient.data.model.IssueResponse
import com.pmacademy.githubclient.data.model.RepoInfoResponse
import com.pmacademy.githubclient.data.model.UserResponse
import com.pmacademy.githubclient.tools.GithubError
import com.pmacademy.githubclient.tools.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class ReposInfoViewModel : ViewModel() {

    private val _repoInfoLiveData = MutableLiveData<Result<RepoInfoResponse, GithubError>>()
    private val _contributorsLiveData = MutableLiveData<Result<List<UserResponse>, GithubError>>()
    private val _issuesLiveData = MutableLiveData<Result<List<IssueResponse>, GithubError>>()

    val repoInfoLiveData: LiveData<Result<RepoInfoResponse, GithubError>> = _repoInfoLiveData
    val contributorsLiveData: LiveData<Result<List<UserResponse>, GithubError>> = _contributorsLiveData
    val issuesLiveData: LiveData<Result<List<IssueResponse>, GithubError>> = _issuesLiveData

    private val githubUtils: GithubUtils by lazy {
        GithubUtils()
    }


    fun getRepoInfo(repoName: String, userName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val repoInfo = githubUtils.getRepoInfo(owner = userName, repo = repoName)
            val contributorsList = githubUtils.getReposContributors(owner = userName, repo = repoName)
            val issueList = githubUtils.getReposIssues(owner = userName, repo = repoName)

            withContext(Dispatchers.Main) {
                _repoInfoLiveData.value = repoInfo
                _contributorsLiveData.value = contributorsList
                _issuesLiveData.value = issueList
            }
        }
    }
}