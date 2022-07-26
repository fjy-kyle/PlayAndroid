package com.example.playandroid.logic.network

import com.example.playandroid.logic.network.service.HomePageService
import com.example.playandroid.logic.network.service.ProjectService

/**
 * 网络数据源访问入口,对所有网络请求统一封装
 */
object PlayAndroidNetwork {

    private val homePageService = ServiceCreator.create(HomePageService::class.java)
        suspend fun getBanner() = homePageService.getBanner()
        suspend fun getArticleList(page: Int) = homePageService.getArticleList(page)

    private val projectPageService = ServiceCreator.create(ProjectService::class.java)
        suspend fun getProjectTree() = projectPageService.getProjectTree()
        suspend fun getProject(page: Int, cid: Int) = projectPageService.getProject(page, cid)

}