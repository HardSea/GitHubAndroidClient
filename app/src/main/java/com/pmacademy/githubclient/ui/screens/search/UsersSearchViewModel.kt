package com.pmacademy.githubclient.ui.screens.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmacademy.githubclient.data.api.GithubUtils
import com.pmacademy.githubclient.data.model.UserResponse
import com.pmacademy.githubclient.tools.GithubError
import com.pmacademy.githubclient.tools.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

private typealias ResultList = Result<List<UserResponse>, GithubError>

@ExperimentalSerializationApi
class UsersSearchViewModel @Inject constructor(private val githubUtils: GithubUtils) : ViewModel() {

    private val _userSearchLiveData = MutableLiveData<ResultList>()
    val userSearchLiveData: LiveData<ResultList> = _userSearchLiveData

    fun getUsersSearch(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = githubUtils.getUsersSearch(username)
            withContext(Dispatchers.Main) {
                _userSearchLiveData.value = result
            }
        }
    }
}
