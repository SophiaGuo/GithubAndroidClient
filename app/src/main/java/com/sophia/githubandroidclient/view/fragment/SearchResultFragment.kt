package com.sophia.githubandroidclient.view.fragment

import android.os.Bundle
import androidx.core.view.isVisible
import com.sophia.githubandroidclient.R
import com.sophia.githubandroidclient.view.baseview.BaseVmFragment
import com.sophia.githubandroidclient.base.view.loadmore.setLoadMoreStatus
import com.sophia.githubandroidclient.base.utils.ActivityHelper
import com.sophia.githubandroidclient.adapter.SearchResultAdapter
import com.sophia.githubandroidclient.adapter.UserAdapter
import com.sophia.githubandroidclient.base.SearchParams.SEARCH_PARAM_TYPE
import com.sophia.githubandroidclient.base.SearchParams.SEARCH_PARAM_TYPE_USER
import com.sophia.githubandroidclient.databinding.LayoutListBinding
import com.sophia.githubandroidclient.view.activity.DetailActivity
import com.sophia.githubandroidclient.viewmodel.SearchResultViewModel

/**
 *  @author: SophiaGuo
 */
class SearchResultFragment : BaseVmFragment<SearchResultViewModel>() {

    companion object {
        fun newInstance(type: String): SearchResultFragment {
                return SearchResultFragment().apply {
                    arguments = Bundle().apply {
                        putString(SEARCH_PARAM_TYPE, type)
                    }
                }
        }
    }
    private val binding: LayoutListBinding by lazy {
        LayoutListBinding.bind(requireView())
    }
    private lateinit var mType: String

    override fun getLayoutId(): Int = R.layout.layout_list

    private lateinit var searchResultAdapter: SearchResultAdapter

    private lateinit var userAdapter: UserAdapter

    override fun viewModelClass() = SearchResultViewModel::class.java

    override fun initIntent() {
        arguments?.run { mType = getString(SEARCH_PARAM_TYPE, "") }
    }

    override fun initView() {
        initAdapter()
        initRefresh()
        initListeners()
    }

    private fun initAdapter() {
        if (mType == SEARCH_PARAM_TYPE_USER) {
            userAdapter = UserAdapter().also {
                it.loadMoreModule.setOnLoadMoreListener {
                    mViewModel.loadMore()
                }

                it.setOnItemClickListener { _, _, position ->
                    val userInfo = it.data[position]
                    ActivityHelper.startActivity(
                        DetailActivity::class.java,
                        mapOf(DetailActivity.PARAM_TITLE to userInfo.login.toString(), DetailActivity.PARAM_URL to userInfo.html_url.toString())
                    )
                }
                binding.recyclerView.adapter = it
            }
        } else {
            searchResultAdapter = SearchResultAdapter().also {
                it.loadMoreModule.setOnLoadMoreListener {
                    mViewModel.loadMore()
                }

                it.setOnItemClickListener { _, _, position ->
                    val repoEntity = it.data[position]
                    ActivityHelper.startActivity(
                        DetailActivity::class.java,
                        mapOf(
                            DetailActivity.PARAM_TITLE to repoEntity.full_name.toString(),
                            DetailActivity.PARAM_URL to repoEntity.html_url.toString()
                        )
                    )
                }
                binding.recyclerView.adapter = it
            }
        }
    }

    private fun initRefresh() {
        binding.swipeRefreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener { mViewModel.search(type = mType) }
        }
    }

    private fun initListeners() {
        binding.reloadView.buttonReload.setOnClickListener {
            mViewModel.search(type = mType)
        }
    }

    override fun observe() {
        super.observe()
        if (mType == SEARCH_PARAM_TYPE_USER) {
            mViewModel.userInfoList.observe(viewLifecycleOwner, {
                userAdapter.setList(it)
            })
        } else {
            mViewModel.repoList.observe(viewLifecycleOwner, {
                searchResultAdapter.setList(it)
            })
        }

        mViewModel.refreshStatus.observe(viewLifecycleOwner, {
            binding.swipeRefreshLayout.isRefreshing = it
        })
        mViewModel.emptyStatus.observe(viewLifecycleOwner, {
            binding.emptyView.includeEmpty.isVisible = it
        })
        mViewModel.loadMoreStatus.observe(viewLifecycleOwner, {
            if (mType == SEARCH_PARAM_TYPE_USER) {
                userAdapter.loadMoreModule.setLoadMoreStatus(it)
            } else {
                searchResultAdapter.loadMoreModule.setLoadMoreStatus(it)
            }
        })
        mViewModel.reloadStatus.observe(viewLifecycleOwner, {
            binding.reloadView.includeReload.isVisible = it
        })

    }

    fun doSearch(searchWords: String) {
        mViewModel.search(searchWords, mType)
    }

}