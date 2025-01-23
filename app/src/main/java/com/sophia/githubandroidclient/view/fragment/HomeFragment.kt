package com.sophia.githubandroidclient.view.fragment

import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.webkit.WebResourceRequest
import android.webkit.WebSettings.FORCE_DARK_OFF
import android.webkit.WebSettings.FORCE_DARK_ON
import android.webkit.WebView
import androidx.fragment.app.Fragment
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import com.just.agentweb.WebChromeClient
import com.just.agentweb.WebViewClient
import com.sophia.githubandroidclient.base.baseview.BaseFragment
import com.sophia.githubandroidclient.R
import com.sophia.githubandroidclient.databinding.FragmentHomeBinding
import com.sophia.githubandroidclient.base.interfaces.ScrollToTop
import com.sophia.githubandroidclient.base.utils.isDarkModeEnabled
import com.sophia.githubandroidclient.store.SettingsStore
import com.sophia.githubandroidclient.base.utils.whiteHostList

/**
 *  @author: SophiaGuo
 */

class HomeFragment : BaseFragment(), ScrollToTop {

    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.bind(requireView())
    }

    private lateinit var fragments: List<Fragment>
    private lateinit var mUrl: String
    private var agentWeb: AgentWeb? = null

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun getLayoutId() = R.layout.fragment_home


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    fun initView() {
        mUrl = "https://github.com/trending"

    }

    fun initData() {
        agentWeb = AgentWeb.with(this)
            .setAgentWebParent(binding.webviewContainer, LayoutParams(-1, -1))
            .useDefaultIndicator(requireContext().getColor(R.color.textColorPrimary), 2)
            .interceptUnkownUrl()
            .setMainFrameErrorView(R.layout.include_reload, R.id.button_reload)
            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)
            .setWebChromeClient(WebChromeClient())
            .setWebViewClient(webViewClient)
            .createAgentWeb()
            .ready()
            .get()
        agentWeb?.webCreator?.webView?.run {
            overScrollMode = WebView.OVER_SCROLL_NEVER
            settings.run {
                javaScriptCanOpenWindowsAutomatically = false
                loadsImagesAutomatically = true
                useWideViewPort = true
                loadWithOverviewMode = true
                textZoom = SettingsStore.getWebTextZoom()

                forceDark = if (isDarkModeEnabled(context)) {
                    FORCE_DARK_ON
                } else {
                    FORCE_DARK_OFF
                }

            }
        }
        agentWeb?.urlLoader?.loadUrl(mUrl)

    }


    override fun handleBackPressed(): Boolean {
        return agentWeb?.back() == true
    }

    private val webViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            return !whiteHostList().contains(request?.url?.host)
        }
    }


    override fun scrollToTop() {
        if (!this::fragments.isInitialized) return
        val currentFragment = fragments[binding.viewPager.currentItem]
        if (currentFragment is ScrollToTop && currentFragment.isVisible) {
            currentFragment.scrollToTop()
        }
    }
}