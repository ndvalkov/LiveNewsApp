package com.homeassignment.livenewsapp

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.homeassignment.livenewsapp.data.db.Article
import com.homeassignment.livenewsapp.ui.components.ArticleDetail
import com.homeassignment.livenewsapp.ui.components.ArticleListItem
import com.homeassignment.livenewsapp.ui.theme.LiveNewsAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3AdaptiveApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()

        setContent {
            LiveNewsAppTheme {

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
                    },
                )
            }
        }
    }
}

@Composable
fun ArticlesList(onArticleClick: (Article) -> Unit) {

    val dummyArticles = List(20) { index ->
        Article(
            author = "Author $index",
            title = "Sample News Title $index",
            description = "This is a sample description of the news article $index. It should provide a brief summary of the content.",
            url = "https://www.ft.com/content/ecfa69df-5d1c-4177-9b14-a3a73072db12",
            urlToImage = "https://fastly.picsum.photos/id/1050/200/200.jpg?hmac=cq2YpkyrMed7ZhJMjcuNfGZtfsZFRhooscxiGkOcgD4",
            publishedAt = "2024-07-09T12:34:56Z",
            content = "This is the content of the sample news article $index.",
            sourceName = "",
            sourceId = ""
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(dummyArticles) { article ->

            ArticleListItem(
                article = article,
                onItemClick = onArticleClick,
                onFavoriteClick = { },
                isFavorite = true
            )

//            Text(
//                text = item.name,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .clickable { onArticleClick(item) }
//                    .padding(8.dp)
//            )
        }
    }
}

@Composable
fun ArticleDetails(article: Article) {

        ArticleDetail(sourceUrl = article.url)

//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        Text(text = "Detail for ${article.title}", modifier = Modifier.padding(8.dp))
//        Text(text = "Item ID: ${article.author}", modifier = Modifier.padding(8.dp))
//    }
}

@Parcelize
data class MyItem(val id: Int, val name: String) : Parcelable


