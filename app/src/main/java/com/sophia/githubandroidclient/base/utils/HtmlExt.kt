package com.sophia.githubandroidclient.base.utils

import androidx.core.text.HtmlCompat

/**
 *  @author: SophiaGuo
 */
fun String?.htmlToSpanned() =
    if (this.isNullOrEmpty()) "" else HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY)