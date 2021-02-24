package com.pmacademy.githubclient.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UsersSearchResponce(
    @SerialName("avatar_url") val avatar_url: String? =null,
//    val events_url: String,
//    val followers_url: String,
//    val following_url: String,
//    val gists_url: String,
//    val gravatar_id: String,
//    val html_url: String,
//    val id: Int,
    @SerialName("login") val login: String? =null,
    @SerialName("name") val name: String? =null,
    @SerialName("id") val id: Int? =null

  //  @SerialName("name") val name:String,
 //   @SerialName("public_repos") val public_repos :Int
//    val node_id: String,
//    val organizations_url: String,
//    val received_events_url: String,
//    val repos_url: String,
//    val score: Int,
//    val site_admin: Boolean,
//    val starred_url: String,
//    val subscriptions_url: String,
//    val type: String,
//    val url: String
) : java.io.Serializable