package com.pmacademy.githubclient.ui.screens.issueinfo

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
import javax.inject.Inject

private typealias ResultListComments = Result<List<IssueCommentResponse>, GithubError>
private typealias ResultCreateResponse = Result<Boolean, GithubError>

@ExperimentalSerializationApi
class IssueCommentsViewModel @Inject constructor(private val githubUtils: GithubUtils) :
    ViewModel() {

    private val _issueCommentsLiveData =
        MutableLiveData<ResultListComments>()
    val issueCommentsLiveData: LiveData<ResultListComments> =
        _issueCommentsLiveData

    private val _createReactionResultLiveData =
        MutableLiveData<ResultCreateResponse>()
    val createReactionResultLiveData: LiveData<ResultCreateResponse> =
        _createReactionResultLiveData

    fun getIssueComments(userName: String, repoName: String, issueNumber: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val repos = githubUtils.getIssueCommentsList(
                owner = userName,
                repo = repoName,
                issueNumber = issueNumber
            )
            withContext(Dispatchers.Main) {
                _issueCommentsLiveData.value = repos
            }
        }
    }

    fun createCommentReaction(
        userName: String,
        repoName: String,
        commentResponse: IssueCommentResponse,
        clickType: ReactionType
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = commentResponse.id?.let { commentId ->
                githubUtils.createReactionForIssueComment(
                    owner = userName,
                    repo = repoName,
                    commentId = commentId,
                    clickType = clickType
                )
            }
            withContext(Dispatchers.Main) {
                _createReactionResultLiveData.value = result
            }
        }
    }
}