package com.sophia.githubandroidclient.base.baseview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 *  @author: SophiaGuo
 *  Describe:
 */
abstract class BaseFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    protected abstract fun getLayoutId(): Int

    open fun handleBackPressed(): Boolean {
        return false
    }

}