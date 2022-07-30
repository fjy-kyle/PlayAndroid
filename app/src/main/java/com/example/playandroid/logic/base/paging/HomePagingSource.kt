package com.example.playandroid.logic.base.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.playandroid.logic.model.ArticleModel
import com.example.playandroid.logic.network.PlayAndroidNetwork

/*
    继承PagingSource时需要指定两个泛型类型，
    此处第一个类型指定为Int，表示页数的数据类型，
    第二个类型表示每一项数据对应的对象类型，此处指定为ArticleModel
* */
class HomePagingSource : BasePagingSource() {
    override suspend fun getArticleList(page: Int): List<ArticleModel> {
        val apiResponse = PlayAndroidNetwork.getArticleList(page)
        // 打乱原有数据再返回，防止每次返回的数据顺序排列固定
        return apiResponse.data.datas.shuffled()
    }
}