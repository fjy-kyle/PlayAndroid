package com.example.playandroid.logic.network.service

import com.example.playandroid.logic.network.ServiceCreator

/**
 * 网络数据源访问入口,对所有网络请求统一封装
 */
object PlayAndroidNetwork {

    private val homePageService = ServiceCreator.create(HomePageService::class.java)

    suspend fun getBanner() = homePageService.getBanner()

    suspend fun getArticleList(page: Int) = homePageService.getArticleList(page)
}