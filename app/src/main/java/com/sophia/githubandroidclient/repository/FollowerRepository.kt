package com.sophia.githubandroidclient.repository

import com.sophia.githubandroidclient.network.RetrofitClient


class FollowerRepository {

    suspend fun getFollowers(userName: String) =
        RetrofitClient.apiService.getFollowers(userName)
}