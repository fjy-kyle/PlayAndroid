package com.example.playandroid.ui.page.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.playandroid.ui.main.PlayActions
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.playandroid.R
import com.example.playandroid.logic.model.*
import com.example.playandroid.ui.page.article.list.ArticleListPaging
import com.example.playandroid.ui.view.PlayAppBar
import com.example.playandroid.ui.view.lce.LcePage
import com.zj.banner.BannerPager

/**
 *  首页布局
 */
@Composable
fun HomePage(
    actions: PlayActions,
    modifier: Modifier = Modifier,
    viewModel: HomePageViewModel = viewModel()
){
    // 从pagingData流收集值，通过collectAsLazyPagingItems()转为可观察State
    val lazyPagingItems = viewModel.articleResult.collectAsLazyPagingItems()
    // 将ViewModel中的LiveData转为Compose可观察的State值，并给LiveData指定初始值。
    val bannerData by viewModel.bannerState.observeAsState(PlayLoading)
    // 获取网络数据，保存在 HomePageViewModel 中。
    viewModel.getData()
    Column(modifier = modifier.fillMaxSize()) {
        PlayAppBar(
            title = stringResource(id = R.string.home_page),
            showBack = false
        )
        LcePage(
            playState = bannerData,
            onErrorClick = { viewModel.getData() }
        ) { // bannerData属于PlaySuccess类则显示以下ui界面
            // Banner
            val data = bannerData as PlaySuccess<List<BannerBean>>
            BannerPager(items = data.data, indicatorGravity = Alignment.BottomEnd)
                {
                    actions.enterArticle(
                        ArticleModel(
                            title = it.title,
                            link = it.url
                        )
                    )
            }
            // ArticleListPaging
            ArticleListPaging(
                listState = rememberLazyListState(),
                lazyPagingItems = lazyPagingItems,
                enterArticle = actions.enterArticle
            )
        }
    }
}