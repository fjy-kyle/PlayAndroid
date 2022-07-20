package com.example.playandroid.logic.base.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.playandroid.logic.model.ArticleModel
import com.example.playandroid.logic.network.service.PlayAndroidNetwork

/*
    继承PagingSource时需要指定两个泛型类型，
    此处第一个类型指定为Int，表示页数的数据类型，
    第二个类型表示每一项数据对应的对象类型，此处指定为ArticleModel
* */
class HomePagingSource : PagingSource<Int, ArticleModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleModel> {
        return try {
            //通过params参数获得key，key表示当前页数
            val page = params.key ?: 1 // 如果为空则将当前页数设置为第 1 页
            // 从服务器获取当前页的数据
            val apiResponse = PlayAndroidNetwork.getArticleList(page)
            // 从服务器返回的数据获得每一个数据项保存到List中
            val articleList = apiResponse.data.datas
            // 获得上一页，并判断如果当前为第一页，则上一页为null
            val prevKey = if (page>1) page - 1 else null
            // 获得下一页，并判断如果当前为最后一页，则下一页为null
            val nextKey = if (articleList.isNotEmpty()) page + 1 else null
            // 最后需要调用LoadResult.Page()函数，构建一个LoadResult对象并返回
            LoadResult.Page(articleList, prevKey, nextKey)
        } catch (e: Exception){
            LoadResult.Error(e)
        }
    }
    // 此方法暂不涉及，返回null即可
    override fun getRefreshKey(state: PagingState<Int, ArticleModel>): Int? = null
}