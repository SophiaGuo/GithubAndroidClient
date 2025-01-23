package com.sophia.githubandroidclient.base.utils

import android.app.ActivityManager
import android.content.Context
import android.os.Process

/**
 *  @author: SophiaGuo
 *  Describe: Process utils
 */

/**
 * Whether is in Main Process
 */
fun isMainProcess(context: Context) = context.packageName == currentProcessName(context)

/**
 * Current process name
 */
private fun currentProcessName(context: Context): String {
    val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    for (process in manager.runningAppProcesses) {
        if (process.pid == Process.myPid()) {
            return process.processName
        }
    }
    return ""
}