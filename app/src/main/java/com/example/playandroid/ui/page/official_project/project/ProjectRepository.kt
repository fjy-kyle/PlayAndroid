package com.example.playandroid.ui.page.official_project.project

import android.app.Application
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.playandroid.logic.base.paging.ProjectPagingSource
import com.example.playandroid.logic.base.repository.BaseArticleRepository
import com.example.playandroid.logic.model.*
import com.example.playandroid.logic.network.PlayAndroidNetwork

class ProjectRepository constructor(private val application: Application):
    BaseArticleRepository(application) {

    /*
        实现获取标题分类方法
     */
    override suspend fun getArticleTree(): BaseModel<List<ClassifyModel>> {
        return PlayAndroidNetwork.getProjectTree()
    }

    /*
            获取PagingList
            注意：因为返回的是和id关联的数据，因此此处需使用query.cid
         */
    override fun getPagingData(query: Query) = Pager(
        PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false
        )
    ) {
        ProjectPagingSource(query.cid)
    }.flow
}