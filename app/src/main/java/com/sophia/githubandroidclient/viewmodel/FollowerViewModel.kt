package com.sophia.githubandroidclient.viewmodel

import androidx.lifecycle.MutableLiveData
import com.sophia.githubandroidclient.base.baseviewmodel.BaseViewModel
import com.sophia.githubandroidclient.repository.FollowerRepository
import com.sophia.githubandroidclient.model.FollowersEntity

/**
 *  @author: SophiaGuo
 */
class FollowerViewModel : BaseViewModel() {

    private val followerRepository by lazy { FollowerRepository() }

    val followerList = MutableLiveData<MutableList<FollowersEntity>>()

    val refreshStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()
    val emptyStatus = MutableLiveData<Boolean>()

    fun getFollowers(userName: String) {
        launch(
            block = {
                refreshStatus.value = true
                emptyStatus.value = false
                reloadStatus.value = false

                val tempFollowerList = followerRepository.getFollowers(userName)
                followerList.value = tempFollowerList
                refreshStatus.value = false
                emptyStatus.value = tempFollowerList.isEmpty()
            },
            error = {
                println(it.message)
                refreshStatus.value = false
                reloadStatus.value = true
            }
        )
    }

}