package com.pmacademy.githubclient.data.api

import com.google.gson.JsonObject
import com.pmacademy.githubclient.data.model.*
import com.pmacademy.myapplicationtemp.data.ReposResponse
import retrofit2.http.*

interface GitHubServiceApi {

    @Headers("Accept: application/vnd.github.v3+json")
    @POST("/login/oauth/access_token")
    @FormUrlEncoded
    suspend fun getAccessToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("code") code: String,
    ): AccessTokenResponse

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/user")
    suspend fun getUser(@Header("Authorization") auth: String): UserResponse

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/users/{username}/repos?sort=pushed")
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
    @GET("/repos/{owner}/{repo}/contents/README.md")
    suspend fun getRepoReadme(
        @Header("Authorization") auth: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String,
    ): ReadmeResponse

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/repos/{owner}/{repo}/issues")
    suspend fun getRepoIssues(
        @Header("Authorization") auth: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): List<IssueResponse>

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/repos/{owner}/{repo}/contributors")
    suspend fun getRepoContributors(
        @Header("Authorization") auth: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): List<UserResponse>


    @Headers("Accept: application/vnd.github.squirrel-girl-preview")
    @POST("/repos/{owner}/{repo}/issues/comments/{comment_id}/reactions")
    suspend fun createReactionForIssueComment(
        @Header("Authorization") auth: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("comment_id") commentId: Int,
        @Body reaction: JsonObject
    )

    /////////  usersSearch
    @Headers("Accept: application/vnd.github.v3+json")
    @GET("search/users")
    suspend fun getUsersSearch(
        @Header("Authorization") auth: String,
        @Query("q") userName: String,
    ): List<UserResponse>
}
