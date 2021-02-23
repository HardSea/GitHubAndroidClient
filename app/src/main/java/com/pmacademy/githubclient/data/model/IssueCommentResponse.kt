package com.pmacademy.githubclient.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IssueCommentResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("body")
    val body: String,
    @SerialName("user")
    val user: UserResponse,
    )