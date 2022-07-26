package com.example.playandroid.logic.network.service

import com.example.playandroid.logic.model.ArticleListModel
import com.example.playandroid.logic.model.BaseModel
import com.example.playandroid.logic.model.ClassifyModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProjectService {
    // 获取项目分类
    @GET("project/tree/json")
    suspend fun getProjectTree(): BaseModel<List<ClassifyModel>>

    // 获取项目文章列表
    @GET("project/list/{page}/json")
    suspend fun getProject(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ) : BaseModel<ArticleListModel>
}