package com.example.playandroid.ui.page.project

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.playandroid.logic.model.*
import com.example.playandroid.ui.main.PlayActions
import com.example.playandroid.ui.page.article.list.ArticleListPaging
import com.example.playandroid.ui.view.ArticleTabRow
import com.example.playandroid.ui.view.lce.LcePage
import com.google.accompanist.insets.statusBarsHeight

@Composable
fun ProjectPage(
    actions: PlayActions,
    modifier: Modifier = Modifier ,
    viewModel: ProjectViewModel = viewModel()
) {
    val lazyPagingItems = viewModel.articleResult.collectAsLazyPagingItems()
    val tree by viewModel.treeState.observeAsState(PlayLoading)
    val position by viewModel.position.observeAsState()
    viewModel.getTree()

    Column(modifier = Modifier.background(color = MaterialTheme.colors.primary)) {
        Spacer(modifier = Modifier.statusBarsHeight())
        LcePage(
            playState = tree,
            onErrorClick = { 
                viewModel.getTree()
            }) { 
            val data = (tree as PlaySuccess<List<ClassifyModel>>).data
            ArticleTabRow(
                position = position,
                data = data) { index, id, isFirst ->
                if (!isFirst) { //不第一次加载时，需保存当前索引
                    // 获得这个与此id关联的数据
                    viewModel.searchArticle(Query(id))
                    viewModel.onPositionChanged(index)
                } else { //第一次加载默认加载分类列表第一项的数据
                    viewModel.searchArticle(Query(id))
                }
            }
        }
        ArticleListPaging(
            modifier,
            listState = rememberLazyListState(),
            lazyPagingItems = lazyPagingItems,
            enterArticle = actions.enterArticle
        )
    }
}