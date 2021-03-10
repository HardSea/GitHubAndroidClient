package com.pmacademy.githubclient.data.api

import com.pmacademy.githubclient.tools.GithubError
import com.pmacademy.githubclient.tools.Result
import javax.inject.Inject


class GitHubInterceptor @Inject constructor() {
    private companion object {
        private const val ERROR_404 = "HTTP 404 "
        private const val ERROR_403 = "HTTP 403 "
        private const val ERROR_401 = "HTTP 401 "
        private const val NO_NETWORK = "Unable to resolve host"
    }

    fun <T> getError(exception: Exception): Result<T, GithubError> {
        if (exception.message?.contains(NO_NETWORK, ignoreCase = true) == true)
            return Result.error(GithubError.NO_NETWORK)
        return when (exception.message) {
            ERROR_404 -> Result.error(GithubError.RESOURCE_NOT_FOUND)
            ERROR_403 -> Result.error(GithubError.API_RATE_LIMIT_EXCEED)
            ERROR_401 -> Result.error(GithubError.UNAUTHORIZED)
            else -> Result.error(GithubError.UNKNOWN_ERROR)
        }
    }
}