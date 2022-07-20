package com.example.playandroid.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {

    private const val BASE_URL = "https://www.wanandroid.com/"
    // 内部create()创建Retrofit
    private fun create(): Retrofit {
        return Retrofit.Builder().apply {
            baseUrl(BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
        }.build()
    }
    // 外部调用create（）返回Service接口的动态代理对象
    fun <T> create(service: Class<T>) : T = create().create(service)
}