package com.sophia.githubandroidclient.base.baseview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ktx.immersionBar
import com.sophia.githubandroidclient.R

/**
 *  @author: SophiaGuo
 *  Describe:
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupStatusBar()
        //instead of ViewBinding
//        setContentView(getLayoutId())
        initView(savedInstanceState)
        initData()
    }

    protected abstract fun getLayoutId(): Int

    protected open fun setupStatusBar() {
        immersionBar {
            fitsSystemWindows(true)
            statusBarColor(R.color.white)
            statusBarDarkFont(true, 0.2f)
        }
    }

    /**
     * About View init
     */
    protected abstract fun initView(savedInstanceState: Bundle?)

    /**
     * About data init
     */
    open fun initData() {

    }

}