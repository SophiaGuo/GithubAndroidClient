package com.sophia.githubandroidclient.view.activity

import android.os.Bundle
import android.view.KeyEvent
import android.view.ViewGroup.LayoutParams
import android.webkit.ConsoleMessage
import android.webkit.WebResourceRequest
import android.webkit.WebSettings.FORCE_DARK_OFF
import android.webkit.WebSettings.FORCE_DARK_ON
import android.webkit.WebView
import com.sophia.githubandroidclient.R
import com.sophia.githubandroidclient.view.baseview.BaseVmActivity
import com.sophia.githubandroidclient.base.utils.ActivityHelper
import com.sophia.githubandroidclient.base.utils.Logger
import com.sophia.githubandroidclient.base.utils.htmlToSpanned
import com.sophia.githubandroidclient.store.SettingsStore
import com.sophia.githubandroidclient.base.utils.whiteHostList
import com.sophia.githubandroidclient.viewmodel.DetailViewModel
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import com.just.agentweb.WebChromeClient
import com.just.agentweb.WebViewClient
import com.sophia.githubandroidclient.databinding.ActivityDetailBinding
import com.sophia.githubandroidclient.base.utils.isDarkModeEnabled

/**
 *  @author: SophiaGuo
 */
class DetailActivity : BaseVmActivity<DetailViewModel>() {

    companion object {
        const val PARAM_TITLE = "param_title"
        const val PARAM_URL = "param_url"
    }

    private lateinit var binding: ActivityDetailBinding


    private lateinit var mTitle: String
    private lateinit var mUrl: String

    private var agentWeb: AgentWeb? = null

    override fun getLayoutId() = R.layout.activity_detail

    override fun viewModelClass() = DetailViewModel::class.java

    override fun initView(savedInstanceState: Bundle?) {
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mTitle = intent?.getStringExtra(PARAM_TITLE) ?: return
        mUrl = intent?.getStringExtra(PARAM_URL) ?: return

        setDetailTitle(mTitle.htmlToSpanned())

        binding.imageBack.setOnClickListener {
            ActivityHelper.finish(DetailActivity::class.java)
        }
    }

    override fun initData() {
        agentWeb = AgentWeb.with(this)
            .setAgentWebParent(binding.webviewContainer, LayoutParams(-1, -1))
            .useDefaultIndicator(getColor(R.color.textColorPrimary), 2)
            .interceptUnkownUrl()
            .setMainFrameErrorView(R.layout.include_reload, R.id.button_reload)
            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)
            .setWebChromeClient(webChromeClient)
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

    private val webChromeClient = object : WebChromeClient() {
        override fun onReceivedTitle(view: WebView?, title: String?) {
            setDetailTitle(title, true)
            super.onReceivedTitle(view, title)
        }

        override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
            Logger.d("WebView", consoleMessage?.message())
            return super.onConsoleMessage(consoleMessage)
        }
    }

    private val webViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            return !whiteHostList().contains(request?.url?.host)
        }
    }

    /**
     * 设置标题
     */
    private fun setDetailTitle(title: CharSequence?, isSelected: Boolean = false) {
        binding.textTitle.text = title
        binding.textTitle.postDelayed({ binding.textTitle.isSelected = isSelected }, 500)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (agentWeb?.handleKeyEvent(keyCode, event) == true) {
            true
        } else {
            super.onKeyDown(keyCode, event)
        }
    }

    override fun onPause() {
        agentWeb?.webLifeCycle?.onPause()
        super.onPause()
    }

    override fun onResume() {
        agentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        agentWeb?.webLifeCycle?.onDestroy()
        super.onDestroy()
    }

    fun refreshPage() {
        agentWeb?.urlLoader?.reload()
    }

}
