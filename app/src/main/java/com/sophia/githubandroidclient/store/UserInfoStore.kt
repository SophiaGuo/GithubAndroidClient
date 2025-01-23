package com.sophia.githubandroidclient.store

import com.sophia.githubandroidclient.GithubApplication
import com.sophia.githubandroidclient.base.utils.MoshiHelper
import com.sophia.githubandroidclient.base.utils.clearSharedPreferenceValue
import com.sophia.githubandroidclient.base.utils.getSharedPreferenceValue
import com.sophia.githubandroidclient.base.utils.putSharedPreferenceValue
import com.sophia.githubandroidclient.model.UserInfo

/**
 *  @author: SophiaGuo
 *  Describe: Save user info
 */
object UserInfoStore {

    private const val SP_USER_INFO = "sp_user_info"
    private const val KEY_USER_INFO = "userInfo"

    /**
     * Get user info from local sharedPreference
     */
    fun getUserInfo(): UserInfo? {
        val userInfoStr = getSharedPreferenceValue(SP_USER_INFO, GithubApplication.instance, KEY_USER_INFO, "")
        return if (userInfoStr.isNotEmpty()) {
            MoshiHelper.fromJson<UserInfo>(userInfoStr)
        } else {
            null
        }
    }

    /**
     * Setting user info, save to sharedPreference
     */
    fun setUserInfo(userInfo: UserInfo) =
        putSharedPreferenceValue(SP_USER_INFO, GithubApplication.instance, KEY_USER_INFO, MoshiHelper.toJson(userInfo))

    /**
     * Clear user info
     */
    fun clearUserInfo() {
        clearSharedPreferenceValue(SP_USER_INFO, GithubApplication.instance)
    }
}

/**
 *  @author: SophiaGuo
 *  Describe: Check is login
 */
fun isLogin() = UserInfoStore.getUserInfo() != null
