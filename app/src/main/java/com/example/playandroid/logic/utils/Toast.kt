package com.example.playandroid.logic.utils

import android.widget.Toast
import androidx.compose.ui.res.stringResource

/**
 *  简化Toast使用
 *  使用方法： “abc”.showToast()
 */

fun String.showToast(){
    Toast.makeText(PlayAndroidApplication.context,this,Toast.LENGTH_SHORT).show()
}

fun Int.showToast(){
    Toast.makeText(PlayAndroidApplication.context, this,Toast.LENGTH_SHORT).show()
}