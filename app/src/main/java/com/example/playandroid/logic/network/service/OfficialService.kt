package com.example.playandroid.logic.network.service

import com.example.playandroid.logic.model.ArticleListModel
import com.example.playandroid.logic.model.BaseModel
import com.example.playandroid.logic.model.ClassifyModel
import retrofit2.http.GET
import retrofit2.http.Path

interface OfficialService {

    @GET("wxarticle/chapters/json")
    suspend fun getWxArticleTree(): BaseModel<List<ClassifyModel>>

    @GET("wxarticle/list/{cid}/{page}/json")
    suspend fun getWxArticle(@Path("cid") cid: Int, @Path("page") page: Int):BaseModel<ArticleListModel>
}