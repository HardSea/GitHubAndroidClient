package com.pmacademy.githubclient.ui.screens.userinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmacademy.githubclient.data.api.GithubUtils
import com.pmacademy.githubclient.data.model.UserResponse
import com.pmacademy.githubclient.tools.GithubError
import com.pmacademy.githubclient.tools.Result
import com.pmacademy.myapplicationtemp.data.ReposResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class UserInfoViewModel : ViewModel() {

    private val _reposLiveData = MutableLiveData<Result<List<ReposResponse>, GithubError>>()
    val reposLiveData: LiveData<Result<List<ReposResponse>, GithubError>> = _reposLiveData

    private val githubUtils: GithubUtils by lazy {
        GithubUtils()
    }

    fun getUserReposList(user: UserResponse, authToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val repos = githubUtils.getUserReposList(user.login, authToken)
            withContext(Dispatchers.Main) {
                _reposLiveData.value = repos
            }
        }
    }
}