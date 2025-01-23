package com.sophia.githubandroidclient.viewmodel

import androidx.lifecycle.MutableLiveData
import com.sophia.githubandroidclient.base.baseviewmodel.BaseViewModel
import com.sophia.githubandroidclient.repository.FollowingRepository
import com.sophia.githubandroidclient.model.FollowersEntity

/**
 *  @author: SophiaGuo
 */
class FollowingViewModel : BaseViewModel() {

    private val followingRepository by lazy { FollowingRepository() }

    val followingList = MutableLiveData<MutableList<FollowersEntity>>()

    val refreshStatus = MutableLiveData<Boolean>()
    val reloadStatus = MutableLiveData<Boolean>()
    val emptyStatus = MutableLiveData<Boolean>()

    fun getFollowing(userName: String) {
        launch(
            block = {

                refreshStatus.value = true
                emptyStatus.value = false
                reloadStatus.value = false

                val tempFollowerList = followingRepository.getFollowing(userName)
                followingList.value = tempFollowerList
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