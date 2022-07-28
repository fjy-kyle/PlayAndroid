package com.example.playandroid.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.playandroid.logic.model.ArticleModel
import com.example.playandroid.ui.main.PlayDestinations.ARTICLE_ROUTE
import com.example.playandroid.ui.main.PlayDestinations.ARTICLE_ROUTE_URL
import com.example.playandroid.ui.main.PlayDestinations.HOME_PAGE_ROUTE
import com.example.playandroid.ui.page.article.ArticlePage
import com.google.gson.Gson
import java.net.URLEncoder

object PlayDestinations {
    const val HOME_PAGE_ROUTE = "home_page_route"
    const val ARTICLE_ROUTE = "article_route" /* 文章详情路线 */
    const val ARTICLE_ROUTE_URL = "article_route_url"
}

/*
    对应用程序中的导航进行建模，内部逻辑均已实现，外部调用只需传参即可
 */
class PlayActions(navController: NavHostController){

    // 进入
    val enterArticle : (ArticleModel) -> Unit = { article ->
        val gson = Gson().toJson(article).trim()
        val result = URLEncoder.encode(gson,"utf-8")
        navController.navigate("${ARTICLE_ROUTE}/$result")
    }
    // 返回
    val upPress: () -> Unit = { navController.navigateUp() }
}


@Composable
fun NavGraph(
    startDestination: String = HOME_PAGE_ROUTE
) {
    val navController = rememberNavController()

    val actions = remember(navController) {
        PlayActions(navController )
    }
    NavHost(navController = navController, startDestination = startDestination, builder = {
        // 首页
        composable(HOME_PAGE_ROUTE) {
            MainPage(actions)
        }
        // 文章详情页
        composable(
            "${ARTICLE_ROUTE}/{$ARTICLE_ROUTE_URL}",
            arguments = listOf(navArgument(ARTICLE_ROUTE_URL){
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val argument = requireNotNull(backStackEntry.arguments)
            // 在详情页获取 ARTICLE_ROUTE_URL
            val parcelable = argument.getString(ARTICLE_ROUTE_URL)
            val fromJson = Gson().fromJson(parcelable, ArticleModel::class.java)
            ArticlePage(
                article = fromJson,
                onBack = actions.upPress
            )
        }
    })
}