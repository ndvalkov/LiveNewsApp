package com.homeassignment.livenewsapp.ui.components

import android.os.Parcelable
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.homeassignment.livenewsapp.data.db.Article
import kotlinx.parcelize.Parcelize

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ListDetailPane(articles: List<Article>) {

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
fun ArticlesList(articles: List<Article>, onArticleClick: (Article) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(articles) { article ->

            ArticleListItem(
                article = article,
                onItemClick = onArticleClick,
                onFavoriteClick = { },
                isFavorite = true
            )
        }
    }
}

@Composable
fun ArticleDetails(article: Article) {
    ArticleDetail(sourceUrl = article.url)
}

@Parcelize
data class MyItem(val id: Int, val name: String) : Parcelable