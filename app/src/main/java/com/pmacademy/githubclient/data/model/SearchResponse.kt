package com.pmacademy.githubclient.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SearchResponse(
    @SerializedName("items")
    val usersList: List<UserResponse>
) : Serializable
