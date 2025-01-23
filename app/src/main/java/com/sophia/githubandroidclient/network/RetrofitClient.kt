package com.sophia.githubandroidclient.network

import com.sophia.githubandroidclient.GithubApplication
import com.sophia.githubandroidclient.base.utils.Logger
import com.sophia.githubandroidclient.base.utils.MoshiHelper
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 *  @author: SophiaGuo
 *  Describe: Retrofit client
 */
object RetrofitClient {

    /**Cookie*/
    private val cookiePersist = SharedPrefsCookiePersistor(GithubApplication.instance)
    private val cookieJar = PersistentCookieJar(SetCookieCache(), cookiePersist)

    /**log**/
    private val logger = HttpLoggingInterceptor.Logger {
        Logger.i(this::class.simpleName, it)
    }
    private val logInterceptor = HttpLoggingInterceptor(logger).apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    /**OkhttpClient*/
    private val okHttpClient = OkHttpClient.Builder()
        .callTimeout(10, TimeUnit.SECONDS)
        .cookieJar(cookieJar)
        .addNetworkInterceptor(logInterceptor)
        .build()

    /**Retrofit*/
    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(ApiService.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(MoshiHelper.moshi))
        .build()

    /**ApiService*/
    val apiService: ApiService = retrofit.create(ApiService::class.java)

    /**Clear cookie*/
    fun clearCookie() = cookieJar.clear()
}