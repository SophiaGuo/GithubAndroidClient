package com.sophia.githubandroidclient.view.activity

import android.os.Bundle
import android.widget.Toast
import com.sophia.githubandroidclient.R
import com.sophia.githubandroidclient.view.baseview.BaseVmActivity
import com.sophia.githubandroidclient.databinding.ActivityLoginBinding
import com.sophia.githubandroidclient.base.utils.ActivityHelper
import com.sophia.githubandroidclient.viewmodel.LoginViewModel

/**
 *  @author: SophiaGuo
 */
class LoginActivity : BaseVmActivity<LoginViewModel>() {

    private lateinit var binding: ActivityLoginBinding

    override fun getLayoutId() = R.layout.activity_login

    override fun viewModelClass() = LoginViewModel::class.java

    override fun initView(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imageClose.setOnClickListener {
            ActivityHelper.finish(LoginActivity::class.java)
        }

        binding.buttonLogin.setOnClickListener {
            val token = binding.edittextToken.text.toString()
            when {
                token.isEmpty() -> Toast.makeText(this, getString(R.string.error_tips_token_empty), Toast.LENGTH_SHORT)
                else -> mViewModel.login(token)
            }
        }
    }

    override fun observe() {
        super.observe()
        mViewModel.run {
            submitting.observe(this@LoginActivity, {
                if (it) showProgressDialog(R.string.logging_in) else dismissProgressDialog()
            })
            loginResult.observe(this@LoginActivity, {
                if (it) {
                    ActivityHelper.finish(LoginActivity::class.java)
                }
            })
        }
    }

}
