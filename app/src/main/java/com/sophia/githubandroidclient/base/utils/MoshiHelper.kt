package com.sophia.githubandroidclient.base.utils

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 *  @author: SophiaGuo
 *  Describe: Parse moshi json
 */
object MoshiHelper {

    /**json parse moshi object **/
    val moshi: Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    /**
     *  json string parse to type T，may be null
     *  @param [json] json string
     */
    inline fun <reified T> fromJson(json: String): T? {
        return moshi.adapter<T>(getType<T>()).fromJson(json)
    }

    /**
     * parse type T to json string
     * @param [t] type T
     */
    inline fun <reified T> toJson(t: T): String {
        return moshi.adapter(T::class.java).toJson(t)
    }

    /**
     * Get type of T
     */
    inline fun <reified T> getType(): Type {
        return (object : TypeToken<T>() {}).type
    }

    /**
     * Get ParameterizedType of T
     */
    abstract class TypeToken<T> {

        /**
         * Type of T
         */
        var type: Type

        init {
            val parameterizedType = this.javaClass.genericSuperclass
                    as ParameterizedType
            type = parameterizedType.actualTypeArguments[0]
        }
    }
}