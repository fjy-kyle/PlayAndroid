package com.example.playandroid.logic.base.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.playandroid.logic.base.repository.BaseArticlePagingRepository
import com.example.playandroid.logic.base.repository.HomeArticlePagingRepository
import com.example.playandroid.logic.model.PlayState
import com.example.playandroid.logic.model.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomePageViewModel(application: Application) : BaseArticleViewModel(application){
    //建立和仓库层的连接
    override val repositoryArticle: BaseArticlePagingRepository
        get() = HomeArticlePagingRepository()
    private var bannerJob: Job? = null
    // 定义banner状态（成功，正在加载，失败）
    private var _bannerState = MutableLiveData<PlayState>()

    val bannerState : LiveData<PlayState> = _bannerState

    // 获取Banner数据和Article数据
    fun getData(){
        getBanner()
        searchArticle(Query())
    }
    // 获取banner数据
    private fun getBanner(){
        bannerJob?.cancel()
        bannerJob = viewModelScope.launch(Dispatchers.IO){
            (repositoryArticle as HomeArticlePagingRepository).getBanner(_bannerState)
        }
    }
}