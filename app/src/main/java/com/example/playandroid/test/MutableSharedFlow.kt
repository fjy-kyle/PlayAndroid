package com.example.playandroid.test

import android.util.Log
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Stack

/**
 * collect() 是一个挂起函数，因此在使用前必须开协程。
 *
 * 以下两者都是热流：（无论有没有订阅者订阅，事件始终都会发生。
 * 热流始终处于活跃状态并将数据存于内存中,及时没有接收者，也会发射数据）
 *
 * MutableSharedFlow()不需要初始值，发射值需要调用 emit()/tryEmit() 方法
 * MutableStateFlow()需要指定初始值
 */
fun main(){
    val mutableSharedFlow = MutableSharedFlow<String>()
    val mutableStateFlow = MutableStateFlow("123")
    runBlocking {
        println("进入launch1")
        launch {
            mutableStateFlow.collect {
                println("collect开始执行")
                println("观察者1：$it")
            }
        }
        println("进入launch2")
        launch {
            mutableSharedFlow.collect {
                println("collect开始执行")
                println("观察者2：$it")
            }
        }
        println("进入launch3")
        launch {
            println("emit开始执行")
            mutableSharedFlow.emit(System.currentTimeMillis().toString()) }
    }
}
/**
 *          进入launch1
            进入launch2
            进入launch3
            collect开始执行
            观察者1：123
            emit开始执行
            collect开始执行
            观察者2：1657643721425
 */