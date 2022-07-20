package com.example.playandroid.ui.view.lce

import android.widget.ProgressBar
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.playandroid.Greeting
import com.example.playandroid.R
import com.example.playandroid.ui.theme.PlayAndroidTheme

/**
 * 加载时显示的画面
 */
@Composable
fun LoadingContent(
    modifier: Modifier = Modifier
){
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // 添加 View 到 Compose
        AndroidView(
            factory = {ProgressBar(it)},
            modifier = Modifier
                .width(200.dp)
                .height(110.dp),
            update = {
                it.indeterminateDrawable =
                   AppCompatResources.getDrawable(context,R.drawable.loading_animation)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LoadingContent()
}