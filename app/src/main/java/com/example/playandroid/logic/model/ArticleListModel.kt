package com.example.playandroid.logic.model

/*
    标题列表数据类
 */
data class ArticleListModel(
    val curPage: Int,
    val datas: List<ArticleModel>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)