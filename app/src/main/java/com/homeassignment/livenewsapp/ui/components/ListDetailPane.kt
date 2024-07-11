package com.homeassignment.livenewsapp.ui.components

import android.os.Parcelable
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.homeassignment.livenewsapp.data.db.Article
import com.homeassignment.livenewsapp.data.db.ArticleEntity
import kotlinx.parcelize.Parcelize

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ListDetailPane(articles: LazyPagingItems<ArticleEntity>) {

    val navigator = rememberListDetailPaneScaffoldNavigator<Article>()

    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }

    ListDetailPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            AnimatedPane {
                ArticlesList(
                    articles = articles,
                    onArticleClick = { item ->
                        navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, item)
                    },
                )
            }
        },
        detailPane = {
            AnimatedPane {
                navigator.currentDestination?.content?.let {
                    ArticleDetails(it)
                }
            }
        }
    )
}

@Composable
fun ArticlesList(articles: LazyPagingItems<ArticleEntity>,
                 onArticleClick: (Article) -> Unit) {

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            contentPadding = PaddingValues(2.dp)
        ) {
            items(count = articles.itemCount) { index ->
                val item = articles[index]
                item?.let {
                    ArticleListItem(
                        article = it.article,
                        onItemClick = onArticleClick,
                        onFavoriteClick = {  },
                        isFavorite = true
                    )
                }
            }
        }

        if (articles.loadState.append is LoadState.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 42.dp)
                    .align(Alignment.BottomCenter),
                contentAlignment = Alignment.Center,
            ) {
                LinearProgressIndicator(modifier = Modifier.height(8.dp))
            }
        }
    }



    // Crashes, duplicates, when scroll back to top (possibly unstable with pagination)
//    LazyVerticalStaggeredGrid(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(4.dp),
//        contentPadding = PaddingValues(2.dp),
//        columns = StaggeredGridCells.Fixed(2)
//    ) {
//
//        items(count = articles.itemCount, key = articles.itemKey { it.article }) { index ->
//            articles[index]?.let {
//                ArticleListItem(
//                    article = it.article,
//                    onItemClick = onArticleClick,
//                    onFavoriteClick = { },
//                    isFavorite = true
//                )
//            }
//        }
//    }
}

@Composable
fun ArticleDetails(article: Article) {
    ArticleDetail(sourceUrl = article.url)
}

@Parcelize
data class MyItem(val id: Int, val name: String) : Parcelable