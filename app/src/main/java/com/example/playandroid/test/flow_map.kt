package com.example.playandroid.test


import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

/**
 * map ： 转换函数，将flow发射出的数据转换成我们想要的数据
 */
// 将Int转成String
fun map() = runBlocking {
    //(1..3).asFlow()
      flow {
          for (i in 1..3){
              delay(1000)
              emit(i)
          }
      }
          .map { "number: $it" }
          .collect{ println(it) }
}
fun transformLatest() = runBlocking {
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

fun flatMapLatest() = runBlocking {
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
    //flatMapLatest()
    //transformLatest()
    map()
}


