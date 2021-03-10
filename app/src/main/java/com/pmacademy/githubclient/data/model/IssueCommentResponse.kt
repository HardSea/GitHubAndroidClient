package com.pmacademy.githubclient.data.model

import com.google.gson.annotations.SerializedName

data class IssueCommentResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("body")
    val body: String?,
    @SerializedName("user")
    val user: UserResponse?,
)