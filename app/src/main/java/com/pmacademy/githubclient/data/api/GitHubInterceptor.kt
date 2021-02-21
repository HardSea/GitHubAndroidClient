package com.pmacademy.githubclient.data.api

import android.util.Log
import com.pmacademy.githubclient.tools.GithubError
import com.pmacademy.githubclient.tools.Result


class GitHubInterceptor {

    companion object {
        private const val ERROR_404 = "HTTP 404 "
    }

    fun <T> getError(exception: Exception): Result<T, GithubError> {
        Log.d("TAG33", "getError: $exception")
        return when (exception.message) {
            ERROR_404 -> Result.error(GithubError.RESOURCE_NOT_FOUND)
            else -> Result.error(GithubError.UNKNOWN_ERROR)
        }
    }
}