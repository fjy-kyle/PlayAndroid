package com.example.playandroid.ui.view

import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.traceEventEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.playandroid.logic.model.ClassifyModel
import com.example.playandroid.logic.utils.getHtmlText

@Composable
fun ArticleTabRow(
    position: Int?,
    data: List<ClassifyModel>,
    onTabClick: (Int, Int, Boolean) -> Unit
) {
    ScrollableTabRow(
        selectedTabIndex = position ?: 0,
        modifier = Modifier.wrapContentWidth(),
        edgePadding = 3.dp,
        indicator = { positions ->
            TabRowDefaults.Indicator(
                Modifier
                    .tabIndicatorOffset(positions[position ?: 0])
                    .requiredWidth(50.dp),
            )
        }
    ) {
        data.forEachIndexed{ index, classifyModel ->
            Tab(
                text = { Text(getHtmlText(classifyModel.name))},
                selected = position == index,
                onClick = {
                    // 如果是第一次加载传入true，反之false
                    onTabClick(index, classifyModel.id, false)
                }
            )
        }
        // 传入时第一次加载所需的参数，将点击事件回调
        onTabClick(0, data[position ?: 0].id, true)
    }
}