package com.sophia.githubandroidclient.base.view.loadmore

import com.chad.library.adapter.base.module.BaseLoadMoreModule

/**
 *  @author: SophiaGuo
 *  Set load more status
 */
fun BaseLoadMoreModule.setLoadMoreStatus(loadMoreStatus: LoadMoreStatus) {
    when (loadMoreStatus) {
        LoadMoreStatus.COMPLETED -> loadMoreComplete()
        LoadMoreStatus.ERROR -> loadMoreFail()
        LoadMoreStatus.END -> loadMoreEnd()
        else -> return
    }
}