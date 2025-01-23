package com.sophia.githubandroidclient.model
/**
 *  @author: SophiaGuo
 */
data class SearchRepoResult(
    val incomplete_results: Boolean,
    val items: MutableList<RepoEntity>,
    val total_count: Int
)