package com.sophia.githubandroidclient.repository

import com.sophia.githubandroidclient.network.RetrofitClient


class FollowingRepository {

    suspend fun getFollowing(userName: String) =
        RetrofitClient.apiService.getFollowing(userName)
}