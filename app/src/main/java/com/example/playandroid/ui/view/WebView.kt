package com.example.playandroid.ui.view

import android.util.Log
import android.webkit.WebView
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver

@Composable
fun rememberWebVIewWithLifecycle(): WebView{
    val context = LocalContext.current
    val webView = remember {
        WebView(context)
    }
    val lifecycleObserver = rememberWebViewLifecycleObserver(webView)
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    //每次lifecycle（生命周期）发生变化，处理和重启效应
    DisposableEffect(lifecycle){
        lifecycle.addObserver(lifecycleObserver)
        //在可组合项从组合树中移除时调用，将 lifecycleObserver 移除避免内存泄漏
        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
        }
    }
    return webView
}

@Composable
private fun rememberWebViewLifecycleObserver(webView: WebView): LifecycleEventObserver =
    remember(webView) {
        LifecycleEventObserver{ _, event ->
            when(event){
                Lifecycle.Event.ON_RESUME -> webView.onResume()
                Lifecycle.Event.ON_PAUSE -> webView.onPause()
                Lifecycle.Event.ON_DESTROY -> webView.destroy()
                else -> Log.d("WebView",event.name)
            }
        }
    }