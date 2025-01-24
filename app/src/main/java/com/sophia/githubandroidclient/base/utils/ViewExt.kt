package com.sophia.githubandroidclient.base.utils

import android.content.Context.INPUT_METHOD_SERVICE
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
*  @author: SophiaGuo
*  Describe:
*/
fun View.hideSoftInput() {
    val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}