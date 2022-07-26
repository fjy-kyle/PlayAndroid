package com.example.playandroid.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel: ViewModel() {

    // 设置初始页面为主页
    private val _position = MutableLiveData(CourseTabs.HOME_PAGE)
    val position: LiveData<CourseTabs> = _position

    fun onPositionChanged(position: CourseTabs) {
        _position.value = position
    }
}