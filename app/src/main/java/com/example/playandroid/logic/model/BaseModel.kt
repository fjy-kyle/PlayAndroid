package com.example.playandroid.logic.model

/*
    基础数据类
 */
data class BaseModel <T> (
    val `data`: T,
    val errorCode: Int,
    val errorMsg: String
)