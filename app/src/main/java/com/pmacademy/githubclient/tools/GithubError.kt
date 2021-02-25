package com.pmacademy.githubclient.tools

import androidx.annotation.StringRes
import com.pmacademy.githubclient.R

enum class GithubError(@StringRes val textErrorRes: Int) {
    RESOURCE_NOT_FOUND(R.string.resource_not_found_text),
    API_RATE_LIMIT_EXCEED(R.string.api_rate_limit_exceed_text),
    UNAUTHORIZED(R.string.user_unauthorize_text),
    NO_NETWORK(R.string.no_network_connection_text),
    UNKNOWN_ERROR(R.string.unknown_error_text)
}