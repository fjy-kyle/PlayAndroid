package com.example.playandroid.logic.model

import com.zj.banner.model.BaseBannerBean

/*
    主页Banner数据类
 */
data class BannerBean(
    val desc: String,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String,
)   :  BaseBannerBean(){
    override val data: Any?
        get() = imagePath
}