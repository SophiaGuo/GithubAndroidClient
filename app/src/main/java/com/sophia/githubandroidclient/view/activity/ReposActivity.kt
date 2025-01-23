package com.sophia.githubandroidclient.view.activity

import android.os.Bundle
import androidx.core.view.isVisible
import com.sophia.githubandroidclient.R
import com.sophia.githubandroidclient.adapter.SearchResultAdapter
import com.sophia.githubandroidclient.view.baseview.BaseVmActivity
import com.sophia.githubandroidclient.databinding.ActivityReposBinding
import com.sophia.githubandroidclient.base.utils.ActivityHelper
import com.sophia.githubandroidclient.store.UserInfoStore
import com.sophia.githubandroidclient.viewmodel.ReposViewModel


/**
 *  @author: SophiaGuo
 */
class ReposActivity : BaseVmActivity<ReposViewModel>() {
    private lateinit var binding: ActivityReposBinding

    override fun getLayoutId() = R.layout.activity_repos

    override fun viewModelClass() = ReposViewModel::class.java

    private lateinit var searchResultAdapter: SearchResultAdapter

    override fun initView(savedInstanceState: Bundle?) {
        binding = ActivityReposBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imageClose.setOnClickListener { ActivityHelper.finish(ReposActivity::class.java) }
        initAdapter()
        initRefresh()
        initListeners()
    }

    private fun initAdapter() {
        searchResultAdapter = SearchResultAdapter().also {

            it.setOnItemClickListener { _, _, position ->
                val repoEntity = it.data[position]
                ActivityHelper.startActivity(
                    DetailActivity::class.java,
                    mapOf(DetailActivity.PARAM_TITLE to repoEntity.full_name.toString(), DetailActivity.PARAM_URL to repoEntity.html_url.toString())
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
        mViewModel.repoList.observe(this@ReposActivity, {
            searchResultAdapter.setList(it)
        })
        mViewModel.refreshStatus.observe(this@ReposActivity, {
            binding.layoutList.swipeRefreshLayout.isRefreshing = it
        })
        mViewModel.emptyStatus.observe(this@ReposActivity, {
            binding.layoutList.emptyView.includeEmpty.isVisible = it
        })
        mViewModel.reloadStatus.observe(this@ReposActivity, {
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
            mViewModel.getPros(it.login!!)
        }
    }

}
