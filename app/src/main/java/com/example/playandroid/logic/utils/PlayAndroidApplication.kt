package com.example.playandroid.logic.utils

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * 提供获取全局 Context 的类
 */
class PlayAndroidApplication : Application(){

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}