package com.example.playandroid.logic.model

/*
    该类定义了数据状态，分别为加载中，加载成功，加载失败。
 */
sealed class PlayState {
}

object PlayLoading : PlayState()
data class PlaySuccess<out T>(val data: T) : PlayState()
data class PlayError(val e: Throwable) : PlayState()