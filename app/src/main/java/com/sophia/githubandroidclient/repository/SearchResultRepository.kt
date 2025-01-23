package com.sophia.githubandroidclient.repository

import com.sophia.githubandroidclient.base.SearchParams.SEARCH_PARAM_TYPE_USER
import com.sophia.githubandroidclient.network.RetrofitClient

/**
 *  @author: SophiaGuo
 */
class SearchResultRepository {

    suspend fun search(keywords: String, page: Int, perPage: Int, type: String) =
        when(type) {
            SEARCH_PARAM_TYPE_USER -> RetrofitClient.apiService.searchUsers(keywords, page, perPage)
            else -> RetrofitClient.apiService.searchRepos(keywords, page, perPage)
        }


}