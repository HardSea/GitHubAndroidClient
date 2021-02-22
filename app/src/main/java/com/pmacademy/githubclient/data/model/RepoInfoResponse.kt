package com.pmacademy.githubclient.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepoInfoResponse(
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String?
)