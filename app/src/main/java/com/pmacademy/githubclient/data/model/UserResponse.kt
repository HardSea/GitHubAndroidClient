package com.pmacademy.githubclient.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    @SerialName("login")
    val login: String,
    @SerialName("avatar_url")
    val avatarUrl: String
): java.io.Serializable