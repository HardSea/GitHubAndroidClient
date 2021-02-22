package com.pmacademy.githubclient.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReadmeResponse(
    @SerialName("content")
    val content: String,
    @SerialName("encoding")
    val encoding: String
)