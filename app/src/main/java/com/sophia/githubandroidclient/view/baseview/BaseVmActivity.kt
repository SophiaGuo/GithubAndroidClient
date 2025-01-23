package com.sophia.githubandroidclient.view.baseview

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.sophia.githubandroidclient.base.baseview.BaseActivity
import com.sophia.githubandroidclient.base.baseviewmodel.BaseViewModel
import com.sophia.githubandroidclient.base.utils.ActivityHelper
import com.sophia.githubandroidclient.store.isLogin
import com.sophia.githubandroidclient.view.activity.LoginActivity
import com.sophia.githubandroidclient.view.fragment.ProgressDialogFragment

/**
 *  @author: SophiaGuo
 *  Describe:
 */
abstract class BaseVmActivity<VM : BaseViewModel> : BaseActivity() {

    private lateinit var progressDialogFragment: ProgressDialogFragment
    protected open lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        observe()
        initViewModelData()
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
    protected abstract fun viewModelClass(): Class<VM>

    /**
     * subscribe，LiveData、Bus
     */
    open fun observe() {

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

    /**
     * Loading dialog
     */
    fun showProgressDialog(@StringRes message: Int) {
        if (!this::progressDialogFragment.isInitialized) {
            progressDialogFragment = ProgressDialogFragment.newInstance()
        }
        if (!progressDialogFragment.isAdded) {
            progressDialogFragment.show(supportFragmentManager, message, false)
        }
    }

    /**
     * Hide loading dialog
     */
    fun dismissProgressDialog() {
        if (this::progressDialogFragment.isInitialized && progressDialogFragment.isVisible) {
            progressDialogFragment.dismissAllowingStateLoss()
        }
    }

    /**
     * About data init
     */
    open fun initViewModelData() {

    }

}
