package com.pmacademy.githubclient.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IssueResponse(
    @SerialName("user")
    val author: UserResponse,
    @SerialName("title")
    val title: String,
    @SerialName("body")
    val body: String,
    @SerialName("number")
    val number: Int
): java.io.Serializable