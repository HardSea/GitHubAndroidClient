package com.pmacademy.githubclient.ui.viewmodel

import android.util.Log
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

@ExperimentalSerializationApi
class ReposInfoViewModel : ViewModel() {

    private val githubUtils: GithubUtils by lazy { GithubUtils() }
    private val _repoInfoLiveData = MutableLiveData<Result<RepoInfoModel, GithubError>>()
    val repoInfoLiveData: LiveData<Result<RepoInfoModel, GithubError>> = _repoInfoLiveData

    fun getRepoInfo(repoName: String, userName: String, authToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val repoInfoModel = GetRepoInfoModelUseCase().invoke(
                repoName = repoName,
                userName = userName,
                authToken = authToken,
                githubUtils = githubUtils
            )

            withContext(Dispatchers.Main) {
                _repoInfoLiveData.value = repoInfoModel
            }
        }
    }
}