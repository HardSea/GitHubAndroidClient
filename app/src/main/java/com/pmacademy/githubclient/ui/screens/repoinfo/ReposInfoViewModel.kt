package com.pmacademy.githubclient.ui.screens.repoinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmacademy.githubclient.data.api.GithubUtils
import com.pmacademy.githubclient.domain.GetRepoInfoModelUseCase
import com.pmacademy.githubclient.domain.RepoInfoModel
import com.pmacademy.githubclient.tools.GithubError
import com.pmacademy.githubclient.tools.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

private typealias ResultRepo = Result<RepoInfoModel, GithubError>

data class RepoInfo(val repoName: String, val userName: String, val authToken: String)

@ExperimentalSerializationApi
class ReposInfoViewModel @Inject constructor(private val githubUtils: GithubUtils) : ViewModel() {

    private val _repoInfoLiveData = MutableLiveData<ResultRepo>()
    val repoInfoLiveData: LiveData<ResultRepo> = _repoInfoLiveData

    fun getRepoInfo(repoInfo: RepoInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            val repoInfoModel = GetRepoInfoModelUseCase().invoke(
                repoName = repoInfo.repoName,
                userName = repoInfo.userName,
                githubUtils = githubUtils
            )

            withContext(Dispatchers.Main) {
                _repoInfoLiveData.value = repoInfoModel
            }
        }
    }
}