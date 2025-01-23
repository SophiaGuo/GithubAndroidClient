package com.sophia.githubandroidclient.store

import com.sophia.githubandroidclient.GithubApplication
import com.sophia.githubandroidclient.base.utils.getSharedPreferenceValue
import com.sophia.githubandroidclient.base.utils.putSharedPreferenceValue

/**
 *  @author: SophiaGuo
 */
object SettingsStore {

    private const val SP_SETTINGS = "sp_settings"
    private const val DEFAULT_WEB_TEXT_ZOOM = 100
    private const val KEY_WEB_TEXT_ZOOM = "key_web_text_zoom"
    private const val KEY_NIGHT_MODE = "key_night_mode"

    fun setWebTextZoom(textZoom: Int) =
        putSharedPreferenceValue(SP_SETTINGS, GithubApplication.instance, KEY_WEB_TEXT_ZOOM, textZoom)

    fun getWebTextZoom() =
        getSharedPreferenceValue(
            SP_SETTINGS,
            GithubApplication.instance,
            KEY_WEB_TEXT_ZOOM,
            DEFAULT_WEB_TEXT_ZOOM
        )

}
