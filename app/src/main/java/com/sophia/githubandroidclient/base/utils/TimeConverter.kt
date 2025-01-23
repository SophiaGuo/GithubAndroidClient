package com.sophia.githubandroidclient.base.utils

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

/**
 *  @author: SophiaGuo
 *  Describe: Time utils
 */
object TimeConverter {

    fun transformTimeAgo(time: String?): String =
            transformTimeStamp(time).let {
                DateUtils.getRelativeTimeSpanString(it).toString()
            }

    private fun transformTimeStamp(time: String?): Long =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
                    .let {
                        it.timeZone = TimeZone.getTimeZone("GMT+1")
                        it.parse(time).time
                    }
}