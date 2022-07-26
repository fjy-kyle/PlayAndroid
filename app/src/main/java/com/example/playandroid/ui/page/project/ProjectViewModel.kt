package com.example.playandroid.ui.page.project

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.playandroid.logic.base.repository.BaseArticlePagingRepository
import com.example.playandroid.logic.base.viewmodel.BaseArticleViewModel
import com.example.playandroid.logic.model.PlayState
import com.example.playandroid.logic.model.Query
import com.example.playandroid.ui.page.home.HomeArticlePagingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProjectViewModel(application: Application) : BaseArticleViewModel(application) {

    override val repositoryArticle: BaseArticlePagingRepository
        get() = ProjectRepository(getApplication())

    // 项目分类状态
    private val _treeState = MutableLiveData<PlayState>()
    val treeState : LiveData<PlayState> = _treeState

    //防止再次进入页面又回到首页，将当前项目分类页码进行保存
    private val _position = MutableLiveData(0)
    val position : LiveData<Int> = _position

    fun onPositionChanged(position: Int) {
        _position.value = position
    }

    // 访问仓库层获取分类数据，保存金_treeState
    fun getTree(){
        viewModelScope.launch(Dispatchers.IO) {
            (repositoryArticle as ProjectRepository).getTree(_treeState)
        }
    }
}