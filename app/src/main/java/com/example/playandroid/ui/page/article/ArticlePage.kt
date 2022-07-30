package com.example.playandroid.ui.page.article

import android.content.Context
import android.content.Intent
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.playandroid.R
import com.example.playandroid.logic.model.ArticleModel
import com.example.playandroid.logic.utils.getHtmlText
import com.example.playandroid.ui.view.PlayAppBar
import com.example.playandroid.ui.view.rememberWebVIewWithLifecycle
import androidx.compose.ui.Modifier
import com.example.playandroid.ui.theme.Purple700

@Composable
fun ArticlePage(
    article: ArticleModel?,
    onBack:() -> Unit
){
    val context = LocalContext.current
    val webView = rememberWebVIewWithLifecycle()
    Scaffold(
        topBar = {
            PlayAppBar(
                title = getHtmlText(article?.title ?: "文章详情"),
                leftClick = {
                    if (webView.canGoBack()){
                        // 返回上一个页面
                        webView.goBack()
                    } else {
                        onBack.invoke()
                    }
                },
                showRight = true,
                rightImg = Icons.Filled.Share,
                rightClick = {
                    sharePost(
                        article?.title,
                        article?.link,
                        context
                    )
                })
        },
        content = { PaddingValues ->
            webContent(
                article,
                webView,
                Modifier.padding(PaddingValues)
            )
        }
    )
}
// 文章分享实现
private fun sharePost(title: String?, post: String?, context: Context){
    if (title == null || post == null) return
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        //将标题和链接进行分享（一些机型可将数据展示在选择页面）
        putExtra(Intent.EXTRA_TITLE, title)
        putExtra(Intent.EXTRA_TEXT, post)
    }
    context.startActivity(
        //使用应用选择器创建
        Intent.createChooser(
            intent,
            context.getString(R.string.share_article)
        )
    )
}

@Composable
private fun webContent(
    article:ArticleModel?,
    webView: WebView,
    modifier: Modifier = Modifier
) {
    val progression = remember {
        mutableStateOf(0f)
    }
    Column(modifier = Modifier.fillMaxSize()) {
        // 设置网页加载进度条
        LinearProgressIndicator(
            progress = progression.value / 100,
            modifier = Modifier.fillMaxWidth(),
            color = Purple700
        )
        AndroidView(
            factory = {
                webView.apply {
                    // 设置页面内打开网页时，在当前页面打开，而不是打开系统浏览器
                    webViewClient = object :WebViewClient(){}
                    loadUrl(article?.link?:"")
                }
            },
            modifier = modifier.fillMaxSize(),
            update = { webView ->
                webView.webChromeClient = object:WebChromeClient(){
                    // 获取网页加载的进度
                    override fun onProgressChanged(view: WebView?, newProgress: Int) {
                        progression.value = newProgress.toFloat()
                        super.onProgressChanged(view, newProgress)
                    }
                }
            }
        )
    }
}