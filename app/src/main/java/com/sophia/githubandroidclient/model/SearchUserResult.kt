package com.sophia.githubandroidclient.model
/**
 *  @author: SophiaGuo
 */
data class SearchUserResult(
    val incomplete_results: Boolean,
    val items: MutableList<UserInfo>,
    val total_count: Int
)