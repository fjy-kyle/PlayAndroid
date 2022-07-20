package com.example.playandroid.ui.view.lce

import androidx.compose.runtime.Composable
import com.example.playandroid.logic.model.PlayError
import com.example.playandroid.logic.model.PlayLoading
import com.example.playandroid.logic.model.PlayState
import com.example.playandroid.logic.model.PlaySuccess

@Composable
fun LcePage(playState: PlayState,onErrorClick:() -> Unit, content:@Composable () -> Unit){
    when(playState) {
        PlayLoading -> {
            LoadingContent()
        }
        is PlayError ->{
            ErrorContent(onErrorClick = onErrorClick)
        }
        is PlaySuccess<*> ->{
            content()
        }
    }
}