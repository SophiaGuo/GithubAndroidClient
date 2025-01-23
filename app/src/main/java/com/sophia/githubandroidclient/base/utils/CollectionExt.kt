package com.sophia.githubandroidclient.base.utils

/**
 *  @author: SophiaGuo
 *  Describe:
 */

fun <T> MutableList<T>?.concat(data: Collection<T>): MutableList<T> {
    val currentList = this?.toList() ?: emptyList()
    val newList = currentList + data
    return newList.toMutableList()
}