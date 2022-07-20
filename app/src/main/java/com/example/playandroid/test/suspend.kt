package com.example.playandroid.test

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * 挂起函数的特点是使用同步的方式完成异步任务
 */
fun main() = runBlocking {
    val start = System.currentTimeMillis()

    val userInfo = getUserInfo()
    println("getUserInfo finish")

    val friendList = getFriendList(userInfo)
    println("getFriendList finish")

    val feedList = getFeedList(friendList)
    println(" getFeedList finish")

    val end  = System.currentTimeMillis()
    println("cost ${end - start} ms")
}

suspend fun getUserInfo(): String {
    runBlocking {
        launch {
            delay(1000L)
        }
        launch {
            delay(1000L)
        }
    }
    return "BoyCoder"
}

suspend fun getFriendList(user: String): String {
        delay(1000L)
    return "friendList"
}

suspend fun getFeedList(friendList: String): String {
        delay(1000L)
    return "feedList"
}
