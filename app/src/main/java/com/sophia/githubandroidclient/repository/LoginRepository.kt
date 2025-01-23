package com.sophia.githubandroidclient.repository

import com.sophia.githubandroidclient.network.RetrofitClient
import com.sophia.githubandroidclient.model.UserInfo

/**
 *  @author: SophiaGuo
 */
class LoginRepository {

    suspend fun login(token: String): UserInfo {
        val auth = "token $token"
        return RetrofitClient.apiService.fetchUserOwner(auth)
    }
}


