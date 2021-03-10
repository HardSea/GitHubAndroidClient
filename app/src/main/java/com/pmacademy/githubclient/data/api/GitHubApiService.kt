package com.pmacademy.githubclient.data.api

import com.pmacademy.githubclient.data.model.*
import com.pmacademy.myapplicationtemp.data.ReposResponse
import retrofit2.http.*

interface GitHubApiService {

    @GET("/user")
    suspend fun getUser(): UserResponse

    @GET("/users/{username}/repos?sort=pushed")
    suspend fun getListUserRepos(
        @Path("username") username: String
    ): List<ReposResponse>

    @GET("/repos/{owner}/{repo}/issues/{issue_number}/comments")
    suspend fun getIssueCommentsList(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("issue_number") issueNumber: Int
    ): List<IssueCommentResponse>

    @GET("/repos/{owner}/{repo}/contents/README.md")
    suspend fun getRepoReadme(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
    ): ReadmeResponse

    @GET("/repos/{owner}/{repo}/issues")
    suspend fun getRepoIssues(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): List<IssueResponse>

    @GET("/repos/{owner}/{repo}/contributors")
    suspend fun getRepoContributors(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): List<UserResponse>

    @Headers("Accept: application/vnd.github.squirrel-girl-preview")
    @POST("/repos/{owner}/{repo}/issues/comments/{comment_id}/reactions")
    suspend fun createReactionForIssueComment(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("comment_id") commentId: Int,
        @Body reaction: Reaction
    )

    @GET("search/users")
    suspend fun getUsersSearch(
        @Query("q") q: String,
    ): SearchResponse
}
