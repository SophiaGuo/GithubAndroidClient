package com.sophia.githubandroidclient.base.baseviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sophia.githubandroidclient.base.exception.ApiException
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

typealias Block<T> = suspend (CoroutineScope) -> T
typealias Error = suspend (Exception) -> Unit
typealias Cancel = suspend (Exception) -> Unit

/**
 *  @author: SophiaGuo
 *  Describe:
 */
open class BaseViewModel : ViewModel() {

    companion object {
        const val LOGIN_STATUS_INVALID = 1  //登录状态失效
        const val NETWORK_EXCEPTION = 2     //网络请求
        const val DATA_ANALYSIS_ERROR = 3   //数据解析
        const val OTHER_ERROR = 4           //其他错误
    }

    val mRequestStatus: MutableLiveData<Int> = MutableLiveData()

    /**
     * Create and execute coroutine
     * @param block execute in coroutine
     * @param error execute when error
     * @param cancel execute when cancel
     * @param showErrorToast Whether to show toast
     * @return Job
     */
    protected fun launch(
        block: Block<Unit>,
        error: Error? = null,
        cancel: Cancel? = null,
        showErrorToast: Boolean = true
    ): Job {
        return viewModelScope.launch {
            try {
                block.invoke(this)
            } catch (e: Exception) {
                when (e) {
                    is CancellationException -> {
                        cancel?.invoke(e)
                    }
                    else -> {
                        onError(e, showErrorToast)
                        error?.invoke(e)
                    }
                }
            }
        }
    }

    /**
     * Create and execute coroutine
     * @param block Execute in coroutine
     * @return Deferred<T>
     */
    protected fun <T> async(block: Block<T>): Deferred<T> {
        return viewModelScope.async { block.invoke(this) }
    }

    /**
     * Cancel coroutine
     * @param job Coroutine job
     */
    protected fun cancelJob(job: Job?) {
        if (job != null && job.isActive && !job.isCompleted && !job.isCancelled) {
            job.cancel()
        }
    }

    /**
     * Handle errors
     * @param e Exception
     * @param showErrorToast Whether to show toast
     */
    private fun onError(e: Exception, showErrorToast: Boolean) {
        when (e) {
            is ApiException -> {
                when (e.code) {
                    //handle login error here
                    -1001 -> {
                        mRequestStatus.value = LOGIN_STATUS_INVALID
                    }
                    // Other error
                    else -> mRequestStatus.value = OTHER_ERROR
                }
            }
            // Network error
            is ConnectException,
            is SocketTimeoutException,
            is UnknownHostException,
            is HttpException,
            is SSLHandshakeException ->
                mRequestStatus.value = NETWORK_EXCEPTION
            // Parse data error
            is JsonDataException, is JsonEncodingException ->
                mRequestStatus.value = DATA_ANALYSIS_ERROR
            // Other error
            else ->
                mRequestStatus.value = OTHER_ERROR
        }
    }
}