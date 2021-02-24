package com.pmacademy.githubclient.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmacademy.githubclient.data.api.GithubUtils
import com.pmacademy.githubclient.data.model.UsersSearchResponce
import com.pmacademy.githubclient.tools.GithubError
import com.pmacademy.githubclient.tools.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class UsersSearchViewModel : ViewModel() {

    private val _userSearchLiveData =
        MutableLiveData<Result<List<UsersSearchResponce>, GithubError>>()
    val userSearchLiveData: LiveData<Result<List<UsersSearchResponce>, GithubError>> =
        _userSearchLiveData

    private val githubUtils: GithubUtils by lazy {
        GithubUtils()
    }

    fun getUsersSearch(username: String, authToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val repos = githubUtils.getUsersSearch(username, authToken)
            withContext(Dispatchers.IO) {
                _userSearchLiveData.postValue(repos)
                println(_userSearchLiveData.value)

                println("lnasldnaskdnalnasldnaskdna" + repos)
                println("lnasldnaskdnalnasldnaskdna" + repos)
            }
        }
    }

}