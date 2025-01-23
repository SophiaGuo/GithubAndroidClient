package com.sophia.githubandroidclient.view.activity

import android.os.Bundle
import androidx.core.view.isVisible
import com.sophia.githubandroidclient.R
import com.sophia.githubandroidclient.view.baseview.BaseVmActivity
import com.sophia.githubandroidclient.base.utils.ActivityHelper
import com.sophia.githubandroidclient.store.UserInfoStore
import com.sophia.githubandroidclient.adapter.FollowersAdapter
import com.sophia.githubandroidclient.databinding.ActivityFollowingBinding
import com.sophia.githubandroidclient.viewmodel.FollowingViewModel

/**
 *  @author: SophiaGuo
 */
class FollowingActivity : BaseVmActivity<FollowingViewModel>() {

    private lateinit var binding: ActivityFollowingBinding

    override fun getLayoutId() = R.layout.activity_following

    override fun viewModelClass() = FollowingViewModel::class.java

    private lateinit var followingAdapter: FollowersAdapter

    override fun initView(savedInstanceState: Bundle?) {
        binding = ActivityFollowingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageClose.setOnClickListener { ActivityHelper.finish(FollowingActivity::class.java) }
        initAdapter()
        initRefresh()
        initListeners()
    }

    private fun initAdapter() {
        followingAdapter = FollowersAdapter().also {

            it.setOnItemClickListener { _, _, position ->
                val followersEntity = it.data[position]
                ActivityHelper.startActivity(
                    DetailActivity::class.java,
                    mapOf(DetailActivity.PARAM_TITLE to followersEntity.login.toString(), DetailActivity.PARAM_URL to followersEntity.html_url.toString())
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
        mViewModel.followingList.observe(this@FollowingActivity, {
            followingAdapter.setList(it)
        })
        mViewModel.refreshStatus.observe(this@FollowingActivity, {
            binding.layoutList.swipeRefreshLayout.isRefreshing = it
        })
        mViewModel.emptyStatus.observe(this@FollowingActivity, {
            binding.layoutList.emptyView.includeEmpty.isVisible = it
        })
        mViewModel.reloadStatus.observe(this@FollowingActivity, {
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
            mViewModel.getFollowing(it.login!!)
        }
    }

}
