package com.pmacademy.myapplicationtemp.data

import com.google.gson.annotations.SerializedName

data class ReposResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("id")
    val id: Int
)