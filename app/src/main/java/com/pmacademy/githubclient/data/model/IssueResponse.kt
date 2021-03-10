package com.pmacademy.githubclient.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class IssueResponse(
    @SerializedName("user")
    val author: UserResponse?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("body")
    val body: String?,
    @SerializedName("number")
    val number: Int?
) : Serializable