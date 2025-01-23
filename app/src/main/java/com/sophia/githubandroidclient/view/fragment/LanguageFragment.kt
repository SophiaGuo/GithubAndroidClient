package com.sophia.githubandroidclient.view.fragment

import androidx.core.view.isVisible
import com.sophia.githubandroidclient.R
import com.sophia.githubandroidclient.view.baseview.BaseVmFragment
import com.sophia.githubandroidclient.base.interfaces.ScrollToTop
import com.sophia.githubandroidclient.base.view.loadmore.setLoadMoreStatus
import com.sophia.githubandroidclient.base.utils.ActivityHelper
import com.sophia.githubandroidclient.adapter.SearchResultAdapter
import com.sophia.githubandroidclient.base.SearchParams.SEARCH_PARAM_TYPE_REPO
import com.sophia.githubandroidclient.databinding.LayoutListBinding
import com.sophia.githubandroidclient.view.activity.DetailActivity
import com.sophia.githubandroidclient.viewmodel.SearchResultViewModel

/**
 *  @author: SophiaGuo
 */
class LanguageFragment : BaseVmFragment<SearchResultViewModel>(), ScrollToTop {

    companion object {
        fun newInstance() = LanguageFragment()
    }

    private val binding: LayoutListBinding by lazy {
        LayoutListBinding.bind(requireView())
    }

    override fun getLayoutId(): Int = R.layout.layout_list

    private lateinit var searchResultAdapter: SearchResultAdapter

    override fun viewModelClass() = SearchResultViewModel::class.java

    private lateinit var mKeyWords: String

    override fun initView() {
        initAdapter()
        initRefresh()
        initListeners()
    }

    private fun initAdapter() {
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

    private fun initRefresh() {
        binding.swipeRefreshLayout.run {
            setColorSchemeResources(R.color.textColorPrimary)
            setProgressBackgroundColorSchemeResource(R.color.bgColorPrimary)
            setOnRefreshListener {
                mViewModel.search(type = SEARCH_PARAM_TYPE_REPO)
            }
        }
    }

    private fun initListeners() {
        binding.reloadView.buttonReload.setOnClickListener {
            mViewModel.search(type = SEARCH_PARAM_TYPE_REPO)
        }
    }

    override fun observe() {
        super.observe()
        mViewModel.repoList.observe(viewLifecycleOwner, {
            searchResultAdapter.setList(it)
        })
        mViewModel.refreshStatus.observe(viewLifecycleOwner, {
            binding.swipeRefreshLayout.isRefreshing = it
        })
        mViewModel.emptyStatus.observe(viewLifecycleOwner, {
            binding.emptyView.includeEmpty.isVisible = it
        })
        mViewModel.loadMoreStatus.observe(viewLifecycleOwner, {
            searchResultAdapter.loadMoreModule.setLoadMoreStatus(it)
        })
        mViewModel.reloadStatus.observe(viewLifecycleOwner, {
            binding.reloadView.includeReload.isVisible = it
        })

    }

    // Lazy load
    override fun lazyLoadData() {
        mKeyWords.let {
            mViewModel.search(mKeyWords, SEARCH_PARAM_TYPE_REPO)
        }
    }

    fun setKeyWord(searchWords: String): LanguageFragment {
        mKeyWords = searchWords
        return this
    }

    override fun scrollToTop() {
        binding.recyclerView.smoothScrollToPosition(0)
    }

}