package com.sophia.githubandroidclient.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.sophia.githubandroidclient.R
import com.sophia.githubandroidclient.view.baseview.BaseVmActivity
import com.sophia.githubandroidclient.databinding.ActivitySettingsBinding
import com.sophia.githubandroidclient.base.utils.ActivityHelper
import com.sophia.githubandroidclient.base.utils.clearCache
import com.sophia.githubandroidclient.base.utils.getCacheSize
import com.sophia.githubandroidclient.store.isLogin
import com.sophia.githubandroidclient.viewmodel.SettingsViewModel

/**
 *  @author: SophiaGuo
 */
@SuppressLint("SetTextI18n")
class SettingsActivity : BaseVmActivity<SettingsViewModel>() {
    private lateinit var binding: ActivitySettingsBinding

    override fun getLayoutId() = R.layout.activity_settings

    override fun viewModelClass() = SettingsViewModel::class.java

    override fun initView(savedInstanceState: Bundle?) {
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textClearCache.text = getCacheSize(this)

        binding.imageBack.setOnClickListener { ActivityHelper.finish(SettingsActivity::class.java) }
        binding.layoutClearCache.setOnClickListener {
            AlertDialog.Builder(this).setMessage(R.string.confirm_clear_cache)
                .setPositiveButton(R.string.confirm) { _, _ ->
                    clearCache(this)
                    binding.textClearCache.text = getCacheSize(this)
                }.setNegativeButton(R.string.cancel) { _, _ -> }.show()
        }
        binding.layoutCheckVersion.setOnClickListener {
            Toast.makeText(this, R.string.already_latest_version, Toast.LENGTH_SHORT).show()
        }

        binding.textLogout.setOnClickListener {
            AlertDialog.Builder(this).setMessage(R.string.confirm_logout)
                .setPositiveButton(R.string.confirm) { _, _ ->
                    mViewModel.logout()
                    ActivityHelper.startActivity(LoginActivity::class.java)
                    ActivityHelper.finish(SettingsActivity::class.java)
                }.setNegativeButton(R.string.cancel) { _, _ -> }.show()
        }
        binding.textLogout.isVisible = isLogin()
    }

}
