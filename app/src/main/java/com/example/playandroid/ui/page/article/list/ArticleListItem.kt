package com.example.playandroid.ui.page.article.list

import android.text.TextUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.playandroid.R
import com.example.playandroid.logic.model.ArticleModel
import com.zj.banner.utils.*
import com.example.playandroid.logic.utils.getHtmlText

/**
 * 列表单项创建
 */
@Composable
fun ArticleItem(
    article: ArticleModel?,
    enterArticle: (urlArgs: ArticleModel) -> Unit
){
    if (article == null) return
    else NewArticleListItem(article = article) {
        enterArticle(article)
    }
}

/**
 * 显示标题列表单项
 */
@Composable
fun ArticleListItem(
    article: ArticleModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    elevation: Dp = 1.dp,
    titleStyle: TextStyle = MaterialTheme.typography.subtitle1
){
    Surface(
        elevation = elevation,
        shape = shape,
        modifier = modifier
    ) {
        Row(modifier = Modifier.clickable(onClick = onClick)) {
            ImageLoader(
                article.envelopePic.ifBlank { R.drawable.img_default }
            )
            Column(
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 16.dp,
                    bottom = 8.dp
                )
            ) {
                Text(
                    text = getHtmlText(article.title),
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 4.dp)
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = article.superChapterName,
                    style = MaterialTheme.typography.caption,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1f)
                        .wrapContentWidth(Alignment.Start)
                )
                Text(
                    text = if (TextUtils.isEmpty(article.author))
                        article.shareUser
                    else article.author,
                    style = MaterialTheme.typography.caption,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1f)
                        .wrapContentWidth(Alignment.Start)
                )
            }
        }
    }
}

/**
 * ArticleListItem 2.0 优化后
 */
@Composable
fun NewArticleListItem(
    article: ArticleModel,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
){
    val hasImage = article.envelopePic.isNotBlank()
    Card(
        modifier = modifier
            .padding(horizontal = 10.dp, vertical = if (hasImage) 7.dp else 5.dp)
            .fillMaxWidth()
            .height(if (hasImage) 90.dp else 71.dp),
        elevation = 5.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = arrayListOf(
                            colorResource(id = R.color.article_item_bg_start),
                            colorResource(id = R.color.article_item_bg_mid),
                            colorResource(id = R.color.article_item_bg_end)
                        )
                    )
                )
                .clickable (onClick = onClick)
        ) {
            if (hasImage){
            ImageLoader(
                data = article.envelopePic,
                Modifier.aspectRatio(1f)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                Text(
                    text = getHtmlText(article.title),
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = if (hasImage) 2 else 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight(Alignment.Top)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight(Alignment.Bottom)
                ) {
                    Text(
                        text = article.superChapterName,
                        style = MaterialTheme.typography.caption,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentWidth(Alignment.Start)
                    )
                    Text(
                        text = if (TextUtils.isEmpty(article.author)) article.shareUser else article.author,
                        style = MaterialTheme.typography.caption,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .weight(1f)
                            .wrapContentWidth(Alignment.Start)
                    )
                }
            }
        }
    }
}