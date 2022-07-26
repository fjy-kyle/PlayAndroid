package com.example.playandroid.logic.network.service

import com.example.playandroid.logic.model.ArticleListModel
import com.example.playandroid.logic.model.BannerBean
import com.example.playandroid.logic.model.BaseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface HomePageService {
    // 获取首页Banner
    @GET("banner/json")
    suspend fun getBanner(): BaseModel<List<BannerBean>>

    // 获取首页文章列表
    @GET("article/list/{page}/json")
    suspend fun getArticleList(
        @Path("page") page: Int
    ) : BaseModel<ArticleListModel>
}