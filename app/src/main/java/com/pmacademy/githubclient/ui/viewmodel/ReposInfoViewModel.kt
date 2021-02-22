package com.pmacademy.githubclient.ui.viewmodel

import android.util.Log
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

    private val _contributorsLiveData = MutableLiveData<Result<List<UserResponse>, GithubError>>()
    private val _issuesLiveData = MutableLiveData<Result<List<IssueResponse>, GithubError>>()
    private val _readmeLiveData = MutableLiveData<Result<String, GithubError>>()

    val contributorsLiveData: LiveData<Result<List<UserResponse>, GithubError>> =
        _contributorsLiveData
    val issuesLiveData: LiveData<Result<List<IssueResponse>, GithubError>> = _issuesLiveData
    val readmeLiveData: LiveData<Result<String, GithubError>> = _readmeLiveData

    private val githubUtils: GithubUtils by lazy {
        GithubUtils()
    }


    fun getRepoInfo(repoName: String, userName: String, authToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val contributorsList = githubUtils.getReposContributors(
                owner = userName,
                repo = repoName,
                authToken = authToken
            )
            val issueList =
                githubUtils.getReposIssues(owner = userName, repo = repoName, authToken = authToken)

            val readmeText =
                githubUtils.getRepoReadme(owner = userName, repo = repoName, authToken = authToken)
            Log.d("TAG333", "getRepoInfo: $readmeText")
            withContext(Dispatchers.Main) {
                _contributorsLiveData.value = contributorsList
                _issuesLiveData.value = issueList
                _readmeLiveData.value = readmeText
            }
        }
    }
}