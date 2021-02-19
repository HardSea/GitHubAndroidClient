package com.pmacademy.githubclient.data.api

import com.pmacademy.githubclient.data.model.AccessToken
import com.pmacademy.myapplicationtemp.data.Repos
import com.pmacademy.myapplicationtemp.data.User
import retrofit2.http.*

interface GitHubServiceApi {

  @Headers("Accept: application/json")
  @POST("login/oauth/access_token")
  @FormUrlEncoded
  suspend fun getAccessToken(
      @Field("client_id") clientId: String,
      @Field("client_secret") clientSecret: String,
      @Field("code") code: String,
  ): AccessToken

  @Headers("Accept: application/vnd.github.v3+json")
  @GET("/user")
  suspend fun getUser(@Header("Authorization") auth: String): User

  @Headers("Accept: application/vnd.github.v3+json")
  @GET("/user/repos")
  suspend fun getUserRepos(@Header("Authorization") auth: String): List<Repos>

}
