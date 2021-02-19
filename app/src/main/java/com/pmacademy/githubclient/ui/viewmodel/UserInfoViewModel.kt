package com.pmacademy.githubclient.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmacademy.githubclient.tools.GithubUtils
import com.pmacademy.myapplicationtemp.data.ReposResponse
import com.pmacademy.githubclient.data.model.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class UserInfoViewModel : ViewModel() {
    private lateinit var viewModel: UserInfoViewModel

    private val _reposLiveData = MutableLiveData<List<ReposResponse>>()
    val reposLiveData: LiveData<List<ReposResponse>> = _reposLiveData
    private val githubUtils: GithubUtils by lazy {
        GithubUtils()
    }


    fun getUserReposList(user: UserResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            val repos = githubUtils.getListUserRepos(user.login)

            withContext(Dispatchers.Main){
                _reposLiveData.postValue(repos)
            }
        }
    }


}