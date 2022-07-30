package com.example.playandroid.ui.page.home

import android.accounts.NetworkErrorException
import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.playandroid.R
import com.example.playandroid.logic.base.paging.HomePagingSource
import com.example.playandroid.logic.base.repository.BaseArticlePagingRepository
import com.example.playandroid.logic.model.*
import com.example.playandroid.logic.network.PlayAndroidNetwork
import com.example.playandroid.logic.utils.NetworkUtils

/**
 * 首页仓库层, 只有两个方法，对应从网络数据源获取 Paging数据和 Banner数据
 * 提供给ViewModel使用，把拿到的数据返回给ViewModel
 */
class HomeArticlePagingRepository (private val application: Application): BaseArticlePagingRepository() {

    /*
     * 这里创建了一个Pager对象，
     * 并调用.flow将它转换成一个Flow对象。在创建Pager对象的时候，
     * 我们指定了PAGE_SIZE，也就是每页所包含的数据量。又指定了pagingSourceFactory，
     * 并将我们自定义的HomePagingSource传入，这样Paging 3就会用它来作为用于分页的数据源了
     * 返回类型是: Flow<PagingData<ArticleModel>>
     */
    override fun getPagingData(query: Query) = Pager(
        PagingConfig(
            // 指定一次加载15条新数据
            pageSize = PAGE_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { HomePagingSource() }
    ).flow

    /*
     * 在getBanner（）方法中将数据的状态传入，然后通过
     * PlayAndroid.getBanner() 获取 Banner数据,
     * 成功的话返回PlaySuccess,失败的话返回PlayError
     */
    suspend fun getBanner(state: MutableLiveData<PlayState>) {
        // 使用postValue 更新state数据，表示接下来要加载数据
        state.postValue(PlayLoading)
        // 先判断网络是否可用，不可用则直接返回
        if (!NetworkUtils.isConnected(context = application)){
            state.postValue(PlayError(NetworkErrorException(
                application.getString(R.string.no_network)) ))
            return
        }
        val bannerResponse = PlayAndroidNetwork.getBanner()
        // 如果errorCode等于0，说明成功返回数据，取出数据放进bannerList，此时数据变化可被观察
        if (bannerResponse.errorCode == 0){
            val bannerList = bannerResponse.data
            state.postValue(PlaySuccess(bannerList))
        }
        // 失败则返回错误信息
        else {
            state.postValue(PlayError(RuntimeException("response status is" +
                    "${bannerResponse.errorCode} msg is ${bannerResponse.errorMsg}")))
        }
    }
}