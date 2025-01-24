package com.sophia.githubandroidclient.view.activity

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.sophia.githubandroidclient.base.baseview.BaseActivity
import com.sophia.githubandroidclient.base.baseview.BaseFragment
import com.sophia.githubandroidclient.base.ON_BACK_PRESS_INTERVAL
import com.sophia.githubandroidclient.R
import com.sophia.githubandroidclient.databinding.ActivityMainBinding
import com.sophia.githubandroidclient.view.fragment.HomeFragment
import com.sophia.githubandroidclient.view.fragment.MeFragment
import com.sophia.githubandroidclient.view.fragment.SearchFragment


/**
 *  @author: SophiaGuo
 *  Describe:
 */
class MainActivity : BaseActivity() {
    private val tag = "MainActivity"

    private lateinit var binding: ActivityMainBinding

    private lateinit var fragments: Map<Int, BaseFragment>
    private var lastClickBackButtonTime = 0L

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestPermissions()
        fragments = mapOf(
            R.id.home to createFragment(HomeFragment::class.java),
            R.id.search to createFragment(SearchFragment::class.java),
            R.id.me to createFragment(MeFragment::class.java)
        )

        binding.bottomNavigationView.run {
            setOnNavigationItemSelectedListener { menuItem ->
                showFragment(menuItem.itemId)
                true
            }
            setOnNavigationItemReselectedListener {
            }
        }

        if (savedInstanceState == null) {
            val initialItemId = R.id.home
            binding.bottomNavigationView.selectedItemId = initialItemId
            showFragment(initialItemId)
        }
    }

    private fun requestPermissions() {
        requestPermissionLauncher.launch(
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Log.i(tag, "Permission Granted")
            } else {
                Log.i(tag, "Permission Denied")
                Toast.makeText(this, R.string.error_deny_permission, Toast.LENGTH_SHORT).show()
            }
        }


    private fun createFragment(clazz: Class<out Fragment>): BaseFragment {
        var fragment =
            supportFragmentManager.fragments.find { it.javaClass == clazz } as BaseFragment?
        if (fragment == null) {
            fragment = when (clazz) {
                HomeFragment::class.java -> HomeFragment.newInstance()
                SearchFragment::class.java -> SearchFragment.newInstance()
                MeFragment::class.java -> MeFragment.newInstance()
                else -> throw IllegalArgumentException("argument ${clazz.simpleName} is illegal")
            }
        }
        return fragment
    }

    private fun showFragment(menuItemId: Int) {
        val currentFragment = supportFragmentManager.fragments.find {
            it.isVisible && it in fragments.values
        }
        val targetFragment = fragments[menuItemId]
        supportFragmentManager.beginTransaction().apply {
            currentFragment?.let { if (it.isVisible) hide(it) }
            targetFragment?.let {
                if (it.isAdded) show(it) else add(R.id.fragment_layout, it)
            }
        }.commit()
    }

    // Press back button two times to quit
    override fun onBackPressed() {
        for (fragment in fragments.values) {
            if (fragment.handleBackPressed())
                return
        }
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickBackButtonTime > ON_BACK_PRESS_INTERVAL) {
            Toast.makeText(this, R.string.press_again_to_exit, Toast.LENGTH_SHORT).show()
            lastClickBackButtonTime = currentTime
        } else {
            super.onBackPressed()
        }
    }
}