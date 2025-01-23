package com.sophia.githubandroidclient.view.activity

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.core.view.isGone
import androidx.core.widget.addTextChangedListener
import com.sophia.githubandroidclient.base.baseview.BaseActivity
import com.sophia.githubandroidclient.base.utils.hideSoftInput
import com.sophia.githubandroidclient.R
import com.sophia.githubandroidclient.base.SearchParams.SEARCH_PARAM_TYPE
import com.sophia.githubandroidclient.base.SearchParams.SEARCH_PARAM_TYPE_REPO
import com.sophia.githubandroidclient.base.SearchParams.SEARCH_PARAM_TYPE_USER
import com.sophia.githubandroidclient.databinding.ActivitySearchBinding
import com.sophia.githubandroidclient.base.utils.ActivityHelper
import com.sophia.githubandroidclient.view.fragment.SearchResultFragment

/**
 *  @author: SophiaGuo
 */
class SearchActivity : BaseActivity() {



    private lateinit var binding: ActivitySearchBinding

    private var mType: String = SEARCH_PARAM_TYPE_REPO

    override fun getLayoutId(): Int = R.layout.activity_search

    override fun initView(savedInstanceState: Bundle?) {
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mType = intent?.getStringExtra(SEARCH_PARAM_TYPE) ?: return
        val resultFragment = SearchResultFragment.newInstance(mType)

        supportFragmentManager.beginTransaction()
            .add(R.id.layout_container, resultFragment)
            .hide(resultFragment)
            .commit()

        binding.imageBack.setOnClickListener {
            if (resultFragment.isVisible) {
                supportFragmentManager
                    .beginTransaction()
                    .hide(resultFragment)
                    .commit()
            } else {
                ActivityHelper.finish(SearchActivity::class.java)
            }
        }
        binding.imageSearch.setOnClickListener {
            val searchWords = binding.edittextSearch.text.toString()
            if (searchWords.isEmpty()) return@setOnClickListener
            it.hideSoftInput()
            resultFragment.doSearch(searchWords)
            supportFragmentManager
                .beginTransaction()
                .show(resultFragment)
                .commit()
        }
        binding.edittextSearch.run {
            addTextChangedListener(afterTextChanged = {
                binding.imageClearSearch.isGone = it.isNullOrEmpty()
            })
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    binding.imageSearch.performClick()
                    true
                } else {
                    false
                }
            }
        }
        binding.imageClearSearch.setOnClickListener { binding.edittextSearch.setText("") }
        binding.edittextSearch.setHint(if (mType == SEARCH_PARAM_TYPE_USER) R.string.search_user_hint else R.string.search_repo_hint)
    }

    override fun onBackPressed() {
        binding.imageBack.performClick()
    }
}