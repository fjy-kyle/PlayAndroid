package com.example.playandroid.test


import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

/**
 * map ： 转换函数，将flow发射出的数据转换成我们想要的数据
 */
// 将Int转成String
fun map() = runBlocking {
    (1..3).asFlow()
        .map { "number: $it" }
        .collect{ println(it) }
}
fun transformlatest() = runBlocking {
    flow {
        emit("a")
        delay(100)
        emit("b")
    }.transformLatest { value ->
            emit(value)
            println(value)
            delay(200)
            emit(value + "_last")
            println(value + "_last")
    }.collect()
}

fun flatmaplatest() = runBlocking {
    flow {
        emit("a")
        delay(100)
        emit("b")
    }.flatMapLatest { value ->
        flow {
            println("flow $value")
            emit(value)
            println(value)
            delay(200)
            emit(value + "_last")
            println(value + "_last")
        }
    }.collect()
}
fun main(){
    flatmaplatest()
    //transformlatest()
}


