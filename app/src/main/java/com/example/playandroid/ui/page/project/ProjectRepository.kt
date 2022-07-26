package com.example.playandroid.ui.page.project

import android.accounts.NetworkErrorException
import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.playandroid.R
import com.example.playandroid.logic.base.paging.ProjectPagingSource
import com.example.playandroid.logic.base.repository.BaseArticlePagingRepository
import com.example.playandroid.logic.model.*
import com.example.playandroid.logic.network.PlayAndroidNetwork
import com.example.playandroid.logic.utils.NetworkUtils

class ProjectRepository constructor(val application: Application): BaseArticlePagingRepository() {
    /*
        获取标题列表
     */
    suspend fun getTree(state: MutableLiveData<PlayState>){
        state.postValue(PlayLoading)
        // 先判断网络是否可用，不可用则直接返回
        if (!NetworkUtils.isConnected(context = application)){
            state.postValue(PlayError(NetworkErrorException(
                application.getString(R.string.no_network))))
            return
        }
        val tree = PlayAndroidNetwork.getProjectTree()
        // errorCode==0 数据成功获取
        if (tree.errorCode == 0){
            val projectList = tree.data
            state.postValue(PlaySuccess(projectList))
        } else {
            state.postValue(PlayError(RuntimeException("response status is" +
                    "${tree.errorCode},msg is ${tree.errorMsg}")))
        }
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