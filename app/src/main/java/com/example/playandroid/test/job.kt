package com.example.playandroid.test

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(){
    runBlocking {
        var myjob = launch {
            delay(1000)
            println("myjob is running")
        }
        println("Hello")
        myjob.join()
        println("world")
    }
}