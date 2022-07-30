package com.example.playandroid.ui.page.official_project.official

import android.app.Application
import com.example.playandroid.logic.base.repository.BaseArticlePagingRepository
import com.example.playandroid.logic.base.viewmodel.BaseArticleViewModelWithTree
import com.example.playandroid.ui.page.official_project.official.OfficialRepository

class OfficialViewModel(application: Application) : BaseArticleViewModelWithTree(application){

    override val repositoryArticle: BaseArticlePagingRepository
        get() = OfficialRepository(getApplication())

    // 访问仓库层获取分类数据，保存金_treeState
    override suspend fun getTreeData(){
        (repositoryArticle as OfficialRepository).getTree(_treeState)
    }
}