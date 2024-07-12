package com.homeassignment.livenewsapp.ui.favorites

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
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
import com.homeassignment.livenewsapp.data.db.FavoriteArticleEntity
import com.homeassignment.livenewsapp.ui.components.ArticleDetails
import com.homeassignment.livenewsapp.ui.components.ArticleListItem
import com.homeassignment.livenewsapp.ui.components.ArticlesList
import com.homeassignment.livenewsapp.ui.components.ListDetailPane

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun FavoritesScreen(favorites: List<FavoriteArticleEntity>) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        val navigator = rememberListDetailPaneScaffoldNavigator<Article>()

        BackHandler(navigator.canNavigateBack()) {
            navigator.navigateBack()
        }

        ListDetailPaneScaffold(
            directive = navigator.scaffoldDirective,
            value = navigator.scaffoldValue,
            listPane = {
                AnimatedPane {
                    FavoritesList(
                        favorites = favorites,
                        onArticleClick = { item ->
                            navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, item)
                        }
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
}

@Composable
fun FavoritesList(
    favorites: List<FavoriteArticleEntity>,
    onArticleClick: (Article) -> Unit
) {
    val favoriteTitles = favorites.map { it.article.title }.toSet()

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            contentPadding = PaddingValues(2.dp)
        ) {
            items(items = favorites, key = { it.article.title }) {
                ArticleListItem(
                    article = it.article,
                    isFavorite = favoriteTitles.contains(it.article.title),
                    onItemClick = onArticleClick
                )
            }
        }
    }
}