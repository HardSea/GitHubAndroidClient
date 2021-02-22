package com.pmacademy.githubclient.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmacademy.githubclient.data.api.GithubUtils
import com.pmacademy.githubclient.data.model.IssueCommentResponse
import com.pmacademy.githubclient.data.model.UserResponse
import com.pmacademy.githubclient.tools.GithubError
import com.pmacademy.githubclient.tools.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class IssueCommentsViewModel : ViewModel() {

    private val _issueCommentsLiveData = MutableLiveData<Result<List<IssueCommentResponse>, GithubError>>()
    val issueCommentsLiveData: LiveData<Result<List<IssueCommentResponse>, GithubError>> = _issueCommentsLiveData

    private val githubUtils: GithubUtils by lazy {
        GithubUtils()
    }

    fun getIssueComments(userName: String, repoName: String, issueNumber: Int, authToken: String) {

        viewModelScope.launch(Dispatchers.IO) {
            val repos = githubUtils.getIssueCommentsList(owner = userName, repo = repoName, issueNumber = issueNumber, authToken = authToken)

            withContext(Dispatchers.Main) {
                _issueCommentsLiveData.value = repos
            }
        }
    }

}