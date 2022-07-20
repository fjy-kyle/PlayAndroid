package com.example.playandroid.ui.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.toUpperCase
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.playandroid.R
import com.example.playandroid.ui.page.home.HomePage
import java.util.Locale

// 主页面
@Composable
fun MainPage(actions: PlayActions, viewModel: HomeViewModel = viewModel()){

    val position by viewModel.position.observeAsState()
    val tabs = CourseTabs.values()

    Scaffold(
        backgroundColor = MaterialTheme.colors.primary,
        bottomBar = {
            BottomNavigation {
                // 配置每个底部单项
                tabs.forEach { tab ->
                     BottomNavigationItem(
                         selected = tab == position,
                         onClick = { viewModel.onPositionChanged(tab) },
                         icon = { Icon(painter = painterResource(id = tab.icon), contentDescription = null)},
                         label = { Text(text = stringResource(id = tab.title).uppercase(Locale.ROOT))},
                         modifier = Modifier.background(MaterialTheme.colors.primary),
                         alwaysShowLabel = true
                     )
                }
            }
        }
    ) {  innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        // 淡入淡出布局切换动画
        Crossfade(targetState = position) { screen ->
            when(screen) {
                CourseTabs.HOME_PAGE -> { HomePage(actions, modifier) }
                CourseTabs.PROJECT -> Text(text = "测试2")// ProjectPage(actions, modifier)
                CourseTabs.OFFICIAL_ACCOUNT -> Text(text = "测试3")// OfficialAccountPage(actions, modifier)
                CourseTabs.MINE -> Text(text = "测试4")// ProfilePage(actions, modifier)
            }
        }
    }
}




enum class CourseTabs(
    @StringRes val title: Int,
    @DrawableRes val icon: Int
) {
    HOME_PAGE(R.string.home_page, R.drawable.ic_nav_news_normal),
    PROJECT(R.string.project, R.drawable.ic_nav_tweet_normal),
    OFFICIAL_ACCOUNT(R.string.official_account, R.drawable.ic_nav_discover_normal),
    MINE(R.string.mine, R.drawable.ic_nav_my_normal)
}