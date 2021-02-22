package com.pmacademy.githubclient.data.api

import com.pmacademy.githubclient.data.model.*
import com.pmacademy.myapplicationtemp.data.ReposResponse
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

    //TODO: add option for sort
    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/users/{username}/repos")
    suspend fun getListUserRepos(
        @Header("Authorization") auth: String,
        @Path("username") username: String
    ): List<ReposResponse>

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/repos/{owner}/{repo}/issues/{issue_number}/comments")
    suspend fun getIssueCommentsList(
        @Header("Authorization") auth: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("issue_number") issueNumber: Int
    ): List<IssueCommentResponse>


    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/repos/{owner}/{repo}")
    suspend fun getRepoInfo(
        @Header("Authorization") auth: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): RepoInfoResponse

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/repos/{owner}/{repo}/issues")
    suspend fun getReposIssues(
        @Header("Authorization") auth: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): List<IssueResponse>

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/repos/{owner}/{repo}/contributors")
    suspend fun getReposContributors(
        @Header("Authorization") auth: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): List<UserResponse>
}
