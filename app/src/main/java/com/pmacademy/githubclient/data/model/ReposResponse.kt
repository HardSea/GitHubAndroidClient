package com.pmacademy.myapplicationtemp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReposResponse(

    @SerialName("name")
    val name: String,
    @SerialName("contributors_url")
    val contributorsUrl: String,
    @SerialName("id")
    val id: Int
)