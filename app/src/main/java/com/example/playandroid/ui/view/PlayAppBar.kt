package com.example.playandroid.ui.view

import android.provider.Contacts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsHeight

/**
 * 通用标题栏，可在其他页面中使用
 */

@Composable
fun PlayAppBar(
    title:String,
    showBack: Boolean = true,
    click:( ()-> Unit )? = null,
    showRight: Boolean = false,
    rightImg: ImageVector = Icons.Rounded.MoreVert,
    rightClick: ( ()->Unit )? = null,
){
    Column(modifier = Modifier.background(color = MaterialTheme.colors.primary)) {
        // 注意此处的statusBarsHeight()使用的是 com.google.accompanist.insets.statusBarsHeight
        Spacer(Modifier.statusBarsHeight())
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(43.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            ) {
            if (showBack && click != null){
                IconButton(
                    modifier = Modifier.
                    wrapContentWidth(Alignment.Start), onClick = click)
                {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = "back")
                }
            }
            Text(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.subtitle1,//设置字体
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            if (showRight && rightClick != null) {
                IconButton(
                    modifier = Modifier.wrapContentWidth(Alignment.End),
                    onClick = rightClick
                ) {
                    Icon(imageVector = rightImg,
                        contentDescription = "more")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlayAppBarPreview() {
        PlayAppBar("测试")
}