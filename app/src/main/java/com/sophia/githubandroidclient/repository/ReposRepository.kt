package com.sophia.githubandroidclient.repository

import com.sophia.githubandroidclient.network.RetrofitClient


class ReposRepository {

    suspend fun getPros(userName: String) =
        RetrofitClient.apiService.getPros(userName)
}