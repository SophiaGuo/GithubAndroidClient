package com.sophia.githubandroidclient

import android.app.Application
import com.sophia.githubandroidclient.base.utils.ActivityHelper
import com.sophia.githubandroidclient.base.utils.isMainProcess
/**
 *  @author: SophiaGuo
 *  Describe:
 */
class GithubApplication: Application() {

    companion object {
        lateinit var instance: GithubApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        // Main process init
        if (isMainProcess(this)) {
            handleInit()
        }
    }

    private fun handleInit() {
        ActivityHelper.init(this)
    }
}