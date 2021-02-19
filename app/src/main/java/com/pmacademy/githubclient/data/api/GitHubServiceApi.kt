package com.pmacademy.githubclient.data.api

import com.pmacademy.githubclient.data.model.AccessTokenResponse
import com.pmacademy.githubclient.data.model.IssueResponse
import com.pmacademy.myapplicationtemp.data.ReposResponse
import com.pmacademy.githubclient.data.model.UserResponse
import retrofit2.http.*

interface GitHubServiceApi {

    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    suspend fun getAccessToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("code") code: String,
    ): AccessTokenResponse

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/user")
    suspend fun getUser(@Header("Authorization") auth: String): UserResponse

//    @Headers("Accept: application/vnd.github.v3+json")
//    @GET("/user/repos")
//    suspend fun getListUserRepos(@Header("Authorization") auth: String): List<ReposResponse>

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/users/{username}/repos")
    suspend fun getListUserRepos(
        @Path("username") username: String
    ): List<ReposResponse>

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/repos/{owner}/{repo}/issues")
    suspend fun getReposIssues(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): List<IssueResponse>

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/repos/{owner}/{repo}/contributors")
    suspend fun getReposContributors(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): List<UserResponse>

}
