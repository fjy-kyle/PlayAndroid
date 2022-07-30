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
abstract class BasePagingSource : PagingSource<Int, ArticleModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleModel> {
        return try {
            //通过params参数获得key，key表示当前页数
            val page = params.key ?: 1 //将第1页设置为默认值
            // 从服务器返回的数据中获得每一个数据项保存到List中
            val articleList = getArticleList(page)
            // 上一页，如果当前页是第一页上一页，则为null
            val prevKey = if (page>1) page - 1 else null
            // 下一页，如果当前页有数据则存在，否则为null
            val nextKey = if (articleList.isNotEmpty()) page + 1 else null
            // 最后需要调用LoadResult.Page()函数，构建一个LoadResult对象并返回
            LoadResult.Page(articleList, prevKey, nextKey)
        } catch (e: Exception){
            LoadResult.Error(e)
        }
    }

    // 此方法暂不涉及，返回null即可
    override fun getRefreshKey(state: PagingState<Int, ArticleModel>): Int? = null

    // 抽象方法，交给外部实现获得数据的逻辑
    abstract suspend fun getArticleList(page: Int) : List<ArticleModel>
}