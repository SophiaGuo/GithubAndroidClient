package com.sophia.githubandroidclient.base.utils

import android.util.Log
import com.sophia.githubandroidclient.BuildConfig

/**
 *  @author: SophiaGuo
 *  Describe: Log utils
 */
object Logger {

    private const val DEFAULT_TAG = "GithubAndroidClient"
    private const val NULL_STRING = "__NULL__"

    @JvmStatic
    @JvmOverloads
    fun d(tag: String? = null, msg: String?) {
        if (BuildConfig.DEBUG) {
            Log.d(tag ?: DEFAULT_TAG, msg ?: NULL_STRING)
        }
    }

    @JvmStatic
    @JvmOverloads
    fun i(tag: String? = null, msg: String?) {
        if (BuildConfig.DEBUG) {
            Log.i(tag ?: DEFAULT_TAG, msg ?: NULL_STRING)
        }
    }

    @JvmStatic
    @JvmOverloads
    fun e(tag: String? = null, msg: String?) {
        if (BuildConfig.DEBUG) {
            Log.e(tag ?: DEFAULT_TAG, msg ?: NULL_STRING)
        }
    }

    @JvmStatic
    @JvmOverloads
    fun w(tag: String? = null, msg: String?) {
        if (BuildConfig.DEBUG) {
            Log.w(tag ?: DEFAULT_TAG, msg ?: NULL_STRING)
        }
    }

    @JvmStatic
    @JvmOverloads
    fun v(tag: String? = null, msg: String?) {
        if (BuildConfig.DEBUG) {
            Log.v(tag ?: DEFAULT_TAG, msg ?: NULL_STRING)
        }
    }

}