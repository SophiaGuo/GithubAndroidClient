package com.sophia.githubandroidclient.viewmodel

import androidx.lifecycle.MutableLiveData
import com.jeremyliao.liveeventbus.LiveEventBus
import com.sophia.githubandroidclient.base.baseviewmodel.BaseViewModel
import com.sophia.githubandroidclient.base.USER_LOGIN_STATE_CHANGED
import com.sophia.githubandroidclient.repository.LoginRepository
import com.sophia.githubandroidclient.store.UserInfoStore

/**
 *  @author: SophiaGuo
 */
class LoginViewModel : BaseViewModel() {

    private val loginRepository by lazy { LoginRepository() }
    val submitting = MutableLiveData<Boolean>()
    val loginResult = MutableLiveData<Boolean>()

    fun login(token: String) {
        launch(
            block = {
                submitting.value = true
                val userInfo = loginRepository.login(token)
                UserInfoStore.setUserInfo(userInfo)
                LiveEventBus.get(USER_LOGIN_STATE_CHANGED).post(true)
                submitting.value = false
                loginResult.value = true
            },
            error = {
                println(it.message)
                submitting.value = false
                loginResult.value = false
            }
        )
    }

}