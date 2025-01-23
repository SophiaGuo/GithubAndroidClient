package com.sophia.githubandroidclient.base.utils

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import com.sophia.githubandroidclient.base.baseview.ActivityLifecycleCallbacksAdapter

/**
 *  @author: SophiaGuo
 */
object ActivityHelper {

    private lateinit var applicationContext: Context

    fun init(application: Application) {
        applicationContext = application.applicationContext
        activities.clear()
        application.registerActivityLifecycleCallbacks(ActivityLifecycleCallbacksAdapter(
            onActivityCreated = { activity, _ ->
                activities.add(activity)
            },
            onActivityDestroyed = { activity ->
                activities.remove(activity)
            }
        ))
    }

    private val activities = mutableListOf<Activity>()

    @JvmOverloads
    fun startActivity(
        clazz: Class<out Activity>,
        params: Map<String, Any> = emptyMap(),
    ) {
        val currentActivity = activities[activities.lastIndex]
        val intent = Intent(currentActivity, clazz)
        params.forEach {
            intent.putExtras(it.key to it.value)
        }
        currentActivity.startActivity(intent)
    }

    /**
     * Finish one or more activities
     */
    fun finish(vararg clazz: Class<out Activity>) {
        activities.forEach { activiy ->
            if (clazz.contains(activiy::class.java)) {
                activiy.finish()
            }
        }
    }

}