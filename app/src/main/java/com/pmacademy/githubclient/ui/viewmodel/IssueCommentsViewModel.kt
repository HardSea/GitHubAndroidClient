package com.pmacademy.githubclient.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmacademy.githubclient.data.api.GithubUtils
import com.pmacademy.githubclient.data.model.IssueCommentResponse
import com.pmacademy.githubclient.data.model.ReactionType
import com.pmacademy.githubclient.tools.GithubError
import com.pmacademy.githubclient.tools.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class IssueCommentsViewModel : ViewModel() {

    private val _issueCommentsLiveData =
        MutableLiveData<Result<List<IssueCommentResponse>, GithubError>>()
    val issueCommentsLiveData: LiveData<Result<List<IssueCommentResponse>, GithubError>> =
        _issueCommentsLiveData

    private val _createReactionResultLiveData =
        MutableLiveData<Result<Boolean, GithubError>>()
    val createReactionResultLiveData: LiveData<Result<Boolean, GithubError>> =
        _createReactionResultLiveData


    private val githubUtils: GithubUtils by lazy {
        GithubUtils()
    }

    fun getIssueComments(userName: String, repoName: String, issueNumber: Int, authToken: String) {

        viewModelScope.launch(Dispatchers.IO) {
            val repos = githubUtils.getIssueCommentsList(
                owner = userName,
                repo = repoName,
                issueNumber = issueNumber,
                authToken = authToken
            )

            withContext(Dispatchers.Main) {
                _issueCommentsLiveData.value = repos
            }
        }
    }

    fun createCommentReaction(
        userName: String,
        repoName: String,
        authToken: String,
        commentResponse: IssueCommentResponse,
        clickType: ReactionType
    ) {
        viewModelScope.launch(Dispatchers.IO) {

            val result = githubUtils.createReactionForIssueComment(
                owner = userName,
                repo = repoName,
                authToken = authToken,
                commentId = commentResponse.id,
                clickType = clickType
            )
            withContext(Dispatchers.Main) {
                _createReactionResultLiveData.value = result
            }
        }
    }

}