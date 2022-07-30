package com.example.playandroid.logic.base.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.playandroid.logic.model.PlayState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * 抽象 ViewModel 用于使用Paging和Tree加载数据的页面
 */
abstract class BaseArticleViewModelWithTree(application: Application): BaseArticleViewModel(application) {
    // 项目分类状态
    protected val _treeState = MutableLiveData<PlayState>()
    val treeState : LiveData<PlayState> = _treeState

    //防止再次进入页面又回到首页，将当前项目分类页码进行保存
    private val _position = MutableLiveData(0)
    val position : LiveData<Int> = _position

    fun onPositionChanged(position: Int) {
        _position.value = position
    }

    abstract suspend fun getTreeData()

    fun getTree(){
        viewModelScope.launch(Dispatchers.IO) {
            getTreeData()
        }
    }
}