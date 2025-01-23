package com.sophia.githubandroidclient.view.fragment

import android.annotation.SuppressLint
import androidx.core.view.*
import com.bumptech.glide.Glide
import com.jeremyliao.liveeventbus.LiveEventBus
import com.sophia.githubandroidclient.R
import com.sophia.githubandroidclient.base.USER_LOGIN_STATE_CHANGED
import com.sophia.githubandroidclient.view.baseview.BaseVmFragment
import com.sophia.githubandroidclient.databinding.FragmentMeBinding
import com.sophia.githubandroidclient.base.utils.ActivityHelper
import com.sophia.githubandroidclient.store.UserInfoStore
import com.sophia.githubandroidclient.store.isLogin
import com.sophia.githubandroidclient.view.activity.FollowersActivity
import com.sophia.githubandroidclient.view.activity.FollowingActivity
import com.sophia.githubandroidclient.view.activity.ReposActivity
import com.sophia.githubandroidclient.view.activity.SettingsActivity
import com.sophia.githubandroidclient.viewmodel.ProfileViewModel

/**
 *  @author: SophiaGuo
 */
class MeFragment : BaseVmFragment<ProfileViewModel>(){

    companion object {
        fun newInstance() = MeFragment()
    }

    private val binding: FragmentMeBinding by lazy {
        FragmentMeBinding.bind(requireView())
    }

    override fun getLayoutId() = R.layout.fragment_me

    override fun viewModelClass(): Class<ProfileViewModel> = ProfileViewModel::class.java

    override fun initView() {
        binding.layoutHeader.setOnClickListener {
            checkLogin {  }
        }
        binding.layoutMyRepos.setOnClickListener {
            checkLogin { ActivityHelper.startActivity(ReposActivity::class.java) }
        }
        binding.layoutFollowers.setOnClickListener {
            checkLogin { ActivityHelper.startActivity(FollowersActivity::class.java) }

        }
        binding.layoutFollowing.setOnClickListener {
            checkLogin { ActivityHelper.startActivity(FollowingActivity::class.java) }
        }
        binding.layoutSettings.setOnClickListener {
            ActivityHelper.startActivity(SettingsActivity::class.java)
        }

        updateUI()
    }

    override fun observe() {
        super.observe()
        LiveEventBus.get(USER_LOGIN_STATE_CHANGED).observe(viewLifecycleOwner, {
            updateUI()
        })
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI() {
        val isLogin = isLogin()
        binding.textLogin.isGone = isLogin
        binding.textNickname.isVisible = isLogin
        binding.textId.isVisible = isLogin
        if (isLogin) {
            val userInfo = UserInfoStore.getUserInfo()
            if (isLogin && userInfo != null) {
                binding.textNickname.text = userInfo.login
                binding.textId.text = userInfo.url
                binding.textReposCount.text = userInfo.public_repos.toString()
                binding.textFollowersCount.text = userInfo.followers.toString()
                binding.textFollowingCount.text = userInfo.following.toString()
                Glide.with(requireActivity()).load(userInfo.avatar_url).placeholder(R.drawable.icon_avatar).into(binding.imageAvatar)
            }
        } else {
            binding.imageAvatar.setImageResource(R.drawable.icon_avatar)
            binding.textReposCount.text = ""
            binding.textFollowersCount.text = ""
            binding.textFollowingCount.text = ""
        }
    }
}