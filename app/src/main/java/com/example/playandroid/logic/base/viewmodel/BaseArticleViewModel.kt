package com.example.playandroid.logic.base.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.playandroid.logic.base.repository.BaseArticlePagingRepository
import com.example.playandroid.logic.model.Query
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import com.example.playandroid.logic.model.ArticleModel

/**
 * 抽象的ViewModel 用于使用Paging加载数据的页面，
 * 之后使用Paging库来加载数据的Viewmodel都可以继承此类进行编写
 */
abstract class BaseArticleViewModel(application: Application) :AndroidViewModel(application){

    abstract val repositoryArticle: BaseArticlePagingRepository

    // MutableSharedFlow 定义一个传输Query类型数据的热流
    // replay 为1 代表重放最新的一个数据，后来的接收器能接受1个最新数据。
    private val searchResults = MutableSharedFlow<Query>(replay = 1)

    // flatMapLatest，该方法可以产生一个新的 Flow，但是只处理最新接收到的值
    // cachedIn()运算符使数据流可共享, 会缓存在它执行之前发生的任何转换的结果
    // 并使用 viewModelScope 缓存加载的数据
    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val articleResult : Flow<PagingData<ArticleModel>> = searchResults.flatMapLatest { query ->
        repositoryArticle.getPagingData(query)
    }.cachedIn(viewModelScope)

    private var searchJob: Job? = null

    open fun searchArticle(query: Query) {
        // 取消协程
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            searchResults.emit(query)
        }
    }
}