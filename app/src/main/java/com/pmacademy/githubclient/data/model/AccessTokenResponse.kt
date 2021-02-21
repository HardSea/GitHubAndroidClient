package com.pmacademy.githubclient.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenResponse(
    @SerialName("access_token") val accessToken: String,
    @SerialName("scope") val scope: String,
    @SerialName("token_type") val tokenType: String,
)