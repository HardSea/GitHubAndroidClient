package com.pmacademy.githubclient.data.api

import android.net.Uri
import com.pmacademy.githubclient.data.api.GithubUtils.Companion.clientId
import com.pmacademy.githubclient.data.api.GithubUtils.Companion.clientSecret
import com.pmacademy.githubclient.data.api.GithubUtils.Companion.loginHost
import com.pmacademy.githubclient.data.api.GithubUtils.Companion.redirectUrl
import com.pmacademy.githubclient.data.api.GithubUtils.Companion.schema
import com.pmacademy.githubclient.data.api.GithubUtils.Companion.scopes
import com.pmacademy.githubclient.data.model.AccessTokenResponse
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
class GitHubLoginUtils @Inject constructor(
    private val loginGithubService: GitHubLoginService
) {

    fun buildAuthGitHubUrl(): Uri {
        return Uri.Builder()
            .scheme(schema)
            .authority(loginHost)
            .appendEncodedPath("login/oauth/authorize")
            .appendQueryParameter("client_id", clientId)
            .appendQueryParameter("scope", scopes)
            .appendQueryParameter("redirect_url", redirectUrl)
            .build()
    }

    fun getCodeFromUri(uri: Uri?): String? {
        uri ?: return null
        if (!uri.toString().startsWith(redirectUrl)) {
            return null
        }
        return uri.getQueryParameter("code")
    }

    suspend fun getAccessToken(code: String): AccessTokenResponse {
        return loginGithubService.getAccessToken(
            clientId,
            clientSecret, code
        )
    }

}