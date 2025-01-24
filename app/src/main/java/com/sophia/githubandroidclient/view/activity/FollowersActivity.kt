package com.sophia.githubandroidclient.view.activity

import android.os.Bundle
import androidx.core.view.isVisible
import com.sophia.githubandroidclient.R
import com.sophia.githubandroidclient.view.baseview.BaseVmActivity
import com.sophia.githubandroidclient.base.utils.ActivityHelper
import com.sophia.githubandroidclient.store.UserInfoStore
import com.sophia.githubandroidclient.adapter.FollowersAdapter
import com.sophia.githubandroidclient.databinding.ActivityFollowerBinding
import com.sophia.githubandroidclient.viewmodel.FollowerViewModel

/**
 *  @author: SophiaGuo
 */
class FollowersActivity : BaseVmActivity<FollowerViewModel>() {

    private lateinit var binding: ActivityFollowerBinding

    override fun getLayoutId() = R.layout.activity_follower

    override fun viewModelClass() = FollowerViewModel::class.java

    private lateinit var followerAdapter: FollowersAdapter

    override fun initView(savedInstanceState: Bundle?) {
        binding = ActivityFollowerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imageClose.setOnClickListener { ActivityHelper.finish(FollowersActivity::class.java) }
        initAdapter()
        initRefresh()
        initListeners()
    }

    private fun initAdapter() {
        followerAdapter = FollowersAdapter().also {

            it.setOnItemClickListener { _, _, position ->
                val followersEntity = it.data[position]
                ActivityHelper.startActivity(
                    DetailActivity::class.java,
                    mapOf(
                        DetailActivity.PARAM_TITLE to followersEntity.login.toString(),
                        DetailActivity.PARAM_URL to followersEntity.html_url
                    )
                )
            }
            binding.layoutList.recyclerView.adapter = it
        }
    }

    private fun initRefresh() {
        binding.layoutList.swipeRefreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)

            setOnRefreshListener {
                getData()
            }
        }
    }

    override fun observe() {
        super.observe()
        mViewModel.followerList.observe(this@FollowersActivity, {
            followerAdapter.setList(it)
        })
        mViewModel.refreshStatus.observe(this@FollowersActivity, {
            binding.layoutList.swipeRefreshLayout.isRefreshing = it
        })
        mViewModel.emptyStatus.observe(this@FollowersActivity, {
            binding.layoutList.emptyView.includeEmpty.isVisible = it
        })
        mViewModel.reloadStatus.observe(this@FollowersActivity, {
            binding.layoutList.reloadView.includeReload.isVisible = it
        })

    }

    private fun initListeners() {
        binding.layoutList.reloadView.buttonReload.setOnClickListener {
            getData()
        }
    }

    override fun initViewModelData() {
        getData()
    }

    private fun getData() {
        val userInfo = UserInfoStore.getUserInfo()
        userInfo?.let {
            mViewModel.getFollowers(it.login!!)
        }
    }

}
