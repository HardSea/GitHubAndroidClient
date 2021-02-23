package com.pmacademy.githubclient.data.model

import com.google.gson.annotations.SerializedName

data class ReadmeResponse(
    @SerializedName("content")
    val content: String,
    @SerializedName("encoding")
    val encoding: String
)