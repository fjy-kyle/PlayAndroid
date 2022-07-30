package com.example.playandroid.logic.base.paging

import com.example.playandroid.logic.model.ArticleModel
import com.example.playandroid.logic.network.PlayAndroidNetwork

class OfficialPagingSource(private val cid:Int): BasePagingSource() {
    override suspend fun getArticleList(page: Int): List<ArticleModel> {
        val apiResponse = PlayAndroidNetwork.getWxArticle(cid, page)
        // 打乱原有数据再返回，防止每次返回的数据顺序排列固定
        return apiResponse.data.datas.shuffled()
    }
}