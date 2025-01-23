package com.sophia.githubandroidclient.network

import com.sophia.githubandroidclient.model.FollowersEntity
import com.sophia.githubandroidclient.model.RepoEntity
import com.sophia.githubandroidclient.model.SearchRepoResult
import com.sophia.githubandroidclient.model.SearchUserResult
import com.sophia.githubandroidclient.model.UserInfo
import retrofit2.http.*

/**
 *  @author: SophiaGuo
 */
interface ApiService {

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }

    /**
     * Login to get owner data
     */
    @GET("user")
    suspend fun fetchUserOwner(@Header("Authorization") authorization: String): UserInfo

    /**
     * Search repos
     */
    @GET("search/repositories")
    suspend fun searchRepos(
        @Query(value="q", encoded = true) query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int): SearchRepoResult

    /**
     * Search users
     */
    @GET("search/users")
    suspend fun searchUsers(
        @Query(value="q", encoded = true) query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int): SearchUserResult

    /**
     * Get users
     */
    @GET("/users")
    suspend fun getUsers(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int): MutableList<UserInfo>

    /**
     * Get owner repos
     */
    @GET("/users/{username}/repos")
    suspend fun getPros(@Path("username") username: String): MutableList<RepoEntity>

    /**
     * Get owner following
     */
    @GET("/users/{username}/following")
    suspend fun getFollowing(@Path("username") username: String): MutableList<FollowersEntity>

    /**
     * Get owner followers
     */
    @GET("/users/{username}/followers")
    suspend fun getFollowers(@Path("username") username: String): MutableList<FollowersEntity>

}