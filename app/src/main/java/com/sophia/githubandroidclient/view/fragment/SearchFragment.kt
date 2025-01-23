package com.sophia.githubandroidclient.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.sophia.githubandroidclient.base.baseview.BaseFragment
import com.sophia.githubandroidclient.R
import com.sophia.githubandroidclient.base.interfaces.ScrollToTop
import com.sophia.githubandroidclient.base.utils.ActivityHelper
import com.sophia.githubandroidclient.adapter.SimpleFragmentPagerAdapter
import com.sophia.githubandroidclient.base.SearchParams.SEARCH_PARAM_TYPE
import com.sophia.githubandroidclient.base.SearchParams.SEARCH_PARAM_TYPE_REPO
import com.sophia.githubandroidclient.base.SearchParams.SEARCH_PARAM_TYPE_USER
import com.sophia.githubandroidclient.databinding.FragmentSearchBinding
import com.sophia.githubandroidclient.view.activity.SearchActivity

/**
 *  @author: SophiaGuo
 */
class SearchFragment : BaseFragment(), ScrollToTop {
    private val binding: FragmentSearchBinding by lazy {
        FragmentSearchBinding.bind(requireView())
    }

    private lateinit var fragments: List<Fragment>
    private var searchType = SEARCH_TYPE_REPO

    companion object {
        fun newInstance() = SearchFragment()
        const val SEARCH_TYPE_REPO = R.string.search_repo
        const val SEARCH_TYPE_USER = R.string.search_user
    }

    override fun getLayoutId() = R.layout.fragment_search

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    fun initView() {
        fragments = listOf(
            LanguageFragment.newInstance().setKeyWord(getString(R.string.fragment_kotlin)),
            LanguageFragment.newInstance().setKeyWord(getString(R.string.fragment_Java)),
            LanguageFragment.newInstance().setKeyWord(getString(R.string.fragment_dart)),
            LanguageFragment.newInstance().setKeyWord(getString(R.string.fragment_cpp)),
            LanguageFragment.newInstance().setKeyWord(getString(R.string.fragment_javascript))
        )
        val titles = listOf<CharSequence>(
            getString(R.string.fragment_kotlin),
            getString(R.string.fragment_Java),
            getString(R.string.fragment_dart),
            getString(R.string.fragment_cpp),
            getString(R.string.fragment_javascript),
        )
        binding.viewPager.adapter = SimpleFragmentPagerAdapter(
            fragmentManager = childFragmentManager,
            fragments = fragments,
            titles = titles
        )
        binding.viewPager.offscreenPageLimit = fragments.size
        binding.tabLayout.setupWithViewPager(binding.viewPager)

        binding.buttonSearchType.setOnClickListener {
            if(searchType == SEARCH_TYPE_REPO) {
                searchType = SEARCH_TYPE_USER
                binding.buttonSearchType.text = getString(R.string.search_user)
                binding.textSearchHint.text = getString(R.string.search_user_hint)
            } else {
                searchType = SEARCH_TYPE_REPO
                binding.buttonSearchType.text = getString(R.string.search_repo)
                binding.textSearchHint.text = getString(R.string.search_repo_hint)
            }
       }

        binding.layoutSearch.setOnClickListener {
            if(searchType == SEARCH_TYPE_REPO) {
                ActivityHelper.startActivity(
                    SearchActivity::class.java, mapOf(
                        SEARCH_PARAM_TYPE to SEARCH_PARAM_TYPE_REPO
                    )
                )
            } else {
                ActivityHelper.startActivity(
                    SearchActivity::class.java, mapOf(
                        SEARCH_PARAM_TYPE to SEARCH_PARAM_TYPE_USER
                    )
                )
            }}

    }

    override fun scrollToTop() {
        if (!this::fragments.isInitialized) return
        val currentFragment = fragments[binding.viewPager.currentItem]
        if (currentFragment is ScrollToTop && currentFragment.isVisible) {
            currentFragment.scrollToTop()
        }
    }
}