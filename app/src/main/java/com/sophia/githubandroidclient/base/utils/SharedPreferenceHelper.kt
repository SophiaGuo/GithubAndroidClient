package com.sophia.githubandroidclient.base.utils

import android.content.Context

/**
 *  @author: SophiaGuo
 *  Describe: SharedPreference utils
 */
private const val SHARED_PREFERENCE_GITHUB_ANDROID_CLIENT = "SharedPreferenceGithubAndroidClient"

@JvmOverloads
fun <T> getSharedPreferenceValue(
    filename: String = SHARED_PREFERENCE_GITHUB_ANDROID_CLIENT,
    context: Context,
    key: String,
    defaultVal: T
): T {
    val sharedPreference = context.getSharedPreferences(filename, Context.MODE_PRIVATE)
    return when (defaultVal) {
        is Boolean -> sharedPreference.getBoolean(key, defaultVal) as T
        is String -> sharedPreference.getString(key, defaultVal) as T
        is Int -> sharedPreference.getInt(key, defaultVal) as T
        is Long -> sharedPreference.getLong(key, defaultVal) as T
        is Float -> sharedPreference.getFloat(key, defaultVal) as T
        is Set<*> -> sharedPreference.getStringSet(key, defaultVal as Set<String>) as T
        else -> throw IllegalArgumentException("Unrecognized default value $defaultVal")
    }
}

@JvmOverloads
fun <T> putSharedPreferenceValue(
    filename: String = SHARED_PREFERENCE_GITHUB_ANDROID_CLIENT,
    context: Context,
    key: String,
    value: T
) {
    val editor = context.getSharedPreferences(filename, Context.MODE_PRIVATE).edit()
    when (value) {
        is Boolean -> editor.putBoolean(key, value)
        is String -> editor.putString(key, value)
        is Int -> editor.putInt(key, value)
        is Long -> editor.putLong(key, value)
        is Float -> editor.putFloat(key, value)
        is Set<*> -> editor.putStringSet(key, value as Set<String>)
        else -> throw UnsupportedOperationException("Unrecognized value $value")
    }
    editor.apply()
}

@JvmOverloads
fun removeSharedPreferenceValue(filename: String = SHARED_PREFERENCE_GITHUB_ANDROID_CLIENT, context: Context, key: String) {
    context.getSharedPreferences(filename, Context.MODE_PRIVATE)
        .edit()
        .remove(key)
        .apply()
}

@JvmOverloads
fun clearSharedPreferenceValue(filename: String = SHARED_PREFERENCE_GITHUB_ANDROID_CLIENT, context: Context) {
    context.getSharedPreferences(filename, Context.MODE_PRIVATE)
        .edit()
        .clear()
        .apply()
}