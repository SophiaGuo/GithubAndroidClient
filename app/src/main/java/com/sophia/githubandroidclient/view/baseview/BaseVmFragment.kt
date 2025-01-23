package com.sophia.githubandroidclient.view.baseview

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.sophia.githubandroidclient.base.baseview.BaseFragment
import com.sophia.githubandroidclient.base.baseviewmodel.BaseViewModel
import com.sophia.githubandroidclient.base.utils.ActivityHelper
import com.sophia.githubandroidclient.store.isLogin
import com.sophia.githubandroidclient.view.activity.LoginActivity

/**
 *  @author: SophiaGuo
 *  Describe:
 */
abstract class BaseVmFragment<VM : BaseViewModel> : BaseFragment() {

    protected lateinit var mViewModel: VM
    private var lazyLoaded = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initIntent()
        initViewModel()
        observe()
        initView()
        initData()
    }

    override fun onResume() {
        super.onResume()
        // Lazy load
        // Run in sub thread
        Thread {
            if (!lazyLoaded) {
                lazyLoadData()
                lazyLoaded = true
            }
        }.start()
    }

    /**
     * Get Intent params
     */
    open fun initIntent() {

    }

    /**
     * Init ViewModel
     */
    private fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(viewModelClass())
    }

    /**
     * Get ViewModel class
     */
    abstract fun viewModelClass(): Class<VM>

    /**
     * subscribe，LiveData、Bus
     */
    open fun observe() {

    }

    /**
     * About init view
     */
    protected abstract fun initView()

    /**
     * About init data
     */
    open fun initData() {

    }

    /**
     * Lazy load data
     */
    open fun lazyLoadData() {

    }

    /**
     * Whether to log in or out, execute 'then' if logged in
     * and jump directly to the login interface if not logged in
     * @return true-already log in，false-not log in
     */
    fun checkLogin(then: (() -> Unit)? = null): Boolean {
        return if (isLogin()) {
            then?.invoke()
            true
        } else {
            ActivityHelper.startActivity(LoginActivity::class.java)
            false
        }
    }

}
