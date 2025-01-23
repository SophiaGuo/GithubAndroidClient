package com.sophia.githubandroidclient.viewmodel

import com.jeremyliao.liveeventbus.LiveEventBus
import com.sophia.githubandroidclient.base.baseviewmodel.BaseViewModel
import com.sophia.githubandroidclient.base.USER_LOGIN_STATE_CHANGED
import com.sophia.githubandroidclient.network.RetrofitClient
import com.sophia.githubandroidclient.store.UserInfoStore
/**
 *  @author: SophiaGuo
 */
class SettingsViewModel : BaseViewModel() {
    fun logout() {
        UserInfoStore.clearUserInfo()
        RetrofitClient.clearCookie()
        LiveEventBus.get(USER_LOGIN_STATE_CHANGED).post(false)
    }
}