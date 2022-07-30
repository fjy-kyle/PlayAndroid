package com.example.playandroid.ui.page.official_project.project

import android.app.Application
import com.example.playandroid.logic.base.repository.BaseArticlePagingRepository
import com.example.playandroid.logic.base.viewmodel.BaseArticleViewModelWithTree

class ProjectViewModel(application: Application) : BaseArticleViewModelWithTree(application) {

    override val repositoryArticle: BaseArticlePagingRepository
        get() = ProjectRepository(getApplication())

    // 访问仓库层获取分类数据，保存金_treeState
    override suspend fun getTreeData(){
            (repositoryArticle as ProjectRepository).getTree(_treeState)
    }
}