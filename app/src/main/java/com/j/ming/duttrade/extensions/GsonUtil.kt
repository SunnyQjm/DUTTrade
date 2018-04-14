package com.j.ming.duttrade.extensions

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

/**
 * Created by Sunny on 2017/4/19 0019.
 */
object GsonUtil {
    private val gson = Gson()

    fun <T> json2Bean(json: String, beanClass: Class<T>): T? {
        var bean: T? = null
        try {
            bean = gson.fromJson(json, beanClass)
        } catch (e: JsonSyntaxException) {
            Log.i("GSON", "json 解析异常：$json")
            e.printStackTrace()
        }

        return bean
    }

    fun bean2Json(`object`: Any): String {
        return gson.toJson(`object`)
    }
}
