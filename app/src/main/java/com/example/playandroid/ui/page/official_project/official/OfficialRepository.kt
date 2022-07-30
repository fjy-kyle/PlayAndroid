package com.example.playandroid.ui.page.official_project.official

import android.app.Application
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.playandroid.logic.base.paging.OfficialPagingSource
import com.example.playandroid.logic.base.repository.BaseArticleRepository
import com.example.playandroid.logic.model.BaseModel
import com.example.playandroid.logic.model.ClassifyModel
import com.example.playandroid.logic.model.Query
import com.example.playandroid.logic.network.PlayAndroidNetwork

class OfficialRepository(private val application: Application):
    BaseArticleRepository(application) {

    override fun getPagingData(query: Query) = Pager(
       PagingConfig(
           pageSize = PAGE_SIZE,
           enablePlaceholders = false
       )
    ){
        OfficialPagingSource(query.cid)
    }.flow

    override suspend fun getArticleTree(): BaseModel<List<ClassifyModel>> {
       return PlayAndroidNetwork.getWxArticleTree()
    }
}