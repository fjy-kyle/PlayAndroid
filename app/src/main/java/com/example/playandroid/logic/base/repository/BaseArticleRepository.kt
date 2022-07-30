package com.example.playandroid.logic.base.repository

import android.accounts.NetworkErrorException
import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.example.playandroid.R
import com.example.playandroid.logic.model.*
import com.example.playandroid.logic.network.PlayAndroidNetwork
import com.example.playandroid.logic.utils.NetworkUtils
import kotlinx.coroutines.flow.Flow

/**
 *  提供获得分类和列表方法的仓库层，继承自 BaseArticlePagingRepository()
 */
abstract class BaseArticleRepository(private val application: Application) : BaseArticlePagingRepository(){

    /*
       获取标题列表
    */
    suspend fun getTree(state: MutableLiveData<PlayState>){
        state.postValue(PlayLoading)
        // 先判断网络是否可用，不可用则直接返回
        if (!NetworkUtils.isConnected(context = application)){
            state.postValue(
                PlayError(
                    NetworkErrorException(
                application.getString(R.string.no_network))
                )
            )
            return
        }
        val tree = getArticleTree()
        // errorCode==0 数据成功获取
        if (tree.errorCode == 0){
            val projectList = tree.data
            state.postValue(PlaySuccess(projectList))
        } else {
            state.postValue(
                PlayError(RuntimeException("response status is" +
                    "${tree.errorCode},msg is ${tree.errorMsg}"))
            )
        }
    }

    abstract suspend fun getArticleTree():BaseModel<List<ClassifyModel>>
}