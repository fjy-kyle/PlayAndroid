package com.example.playandroid.logic.base.repository

import androidx.paging.PagingData
import com.example.playandroid.logic.model.ArticleModel
import com.example.playandroid.logic.model.Query
import kotlinx.coroutines.flow.Flow

/**
 * 基础仓库层，提供获得列表方法
 */
abstract class BaseArticlePagingRepository {
    companion object{
        const val PAGE_SIZE = 15
    }

    abstract fun getPagingData(query: Query): Flow<PagingData<ArticleModel>>
}