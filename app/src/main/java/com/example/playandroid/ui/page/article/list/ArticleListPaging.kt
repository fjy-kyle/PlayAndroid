package com.example.playandroid.ui.page.article.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.example.playandroid.logic.model.ArticleModel
import com.example.playandroid.logic.utils.showToast
import com.example.playandroid.ui.view.lce.ErrorContent
import com.example.playandroid.ui.view.lce.LoadingContent

@Composable
fun ArticleListPaging(
    modifier: Modifier = Modifier,
    listState: LazyListState,
    lazyPagingItems: LazyPagingItems<ArticleModel>,
    enterArticle: (ArticleModel) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        state = listState
    ) {
        items(lazyPagingItems) { article ->
            ArticleItem(article) { urlArgs ->
                //传入进入文章详情页所需数据（ArticleModel）
                enterArticle(urlArgs)
            }
        }
        val loadStates = lazyPagingItems.loadState
        when {
            //刷新正在加载时的页面
            loadStates.refresh is LoadState.Loading -> {
                item { LoadingContent(modifier = Modifier.fillParentMaxSize()) }
            }
            // 添加正在加载时的页面
            loadStates.append is LoadState.Loading -> {
                item { LoadingContent() }
            }
            // 刷新出现错误时的页面
            loadStates.refresh is LoadState.Error -> {
                val e = lazyPagingItems.loadState.refresh as LoadState.Error
                e.error.localizedMessage ?: "".showToast()
                item {
                    ErrorContent(modifier = Modifier.fillParentMaxSize()) {
                        lazyPagingItems.retry()
                    }
                }
            }
            // 添加出现错误时的页面
            loadStates.append is LoadState.Error -> {
                val e = lazyPagingItems.loadState.append as LoadState.Error
                e.error.localizedMessage ?: "".showToast()
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = { lazyPagingItems.retry() }) {
                            Text(text = "Retry")
                        }
                    }
                }
            }
        }
    }
}