package com.sophia.githubandroidclient.viewmodel

import androidx.lifecycle.MutableLiveData
import com.sophia.githubandroidclient.base.baseviewmodel.BaseViewModel
import com.sophia.githubandroidclient.base.view.loadmore.LoadMoreStatus
import com.sophia.githubandroidclient.repository.ReposRepository
import com.sophia.githubandroidclient.model.RepoEntity

/**
 *  @author: SophiaGuo
 */
class ReposViewModel : BaseViewModel() {


    private val reposRepository by lazy { ReposRepository() }

    val repoList = MutableLiveData<MutableList<RepoEntity>>()

    val refreshStatus = MutableLiveData<Boolean>()
    val loadMoreStatus = MutableLiveData<LoadMoreStatus>()
    val reloadStatus = MutableLiveData<Boolean>()
    val emptyStatus = MutableLiveData<Boolean>()

    fun getPros(userName: String) {
        launch(
            block = {
                refreshStatus.value = true
                emptyStatus.value = false
                reloadStatus.value = false

                val reposList = reposRepository.getPros(userName)
                repoList.value = reposList
                refreshStatus.value = false
                emptyStatus.value = reposList.isEmpty()
            },
            error = {
                println(it.message)
                refreshStatus.value = false
                reloadStatus.value = true
            }
        )
    }

}