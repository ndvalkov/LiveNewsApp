package com.homeassignment.livenewsapp.ui.home

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.homeassignment.livenewsapp.data.db.ArticleEntity
import com.homeassignment.livenewsapp.ui.components.ListDetailPane

@Composable
fun HomeScreen(allArticles: LazyPagingItems<ArticleEntity>) {
    Surface(modifier = Modifier.fillMaxSize()) {
        when {
            allArticles.loadState.refresh is LoadState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            allArticles.loadState.refresh is LoadState.Error -> {
                val error = (allArticles.loadState.refresh as LoadState.Error).error
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error: ${error.localizedMessage}")
                }
            }
            allArticles.itemCount == 0 -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No articles available")
                }
            }
            else -> {
                ListDetailPane(articles = allArticles)
            }
        }

        if (allArticles.loadState.append is LoadState.Error || allArticles.loadState.prepend is LoadState.Error) {
            Toast.makeText(
                LocalContext.current,
                "Error loading more items: ${(allArticles.loadState.append as LoadState.Error).error.localizedMessage}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

//val dummyArticles = List(20) { index ->
//    Article(
//        author = "Author $index",
//        title = "Sample News Title $index",
//        description = "This is a sample description of the news article $index. It should provide a brief summary of the content.",
//        url = "https://www.ft.com/",
//        urlToImage = "https://fastly.picsum.photos/id/1050/200/200.jpg?hmac=cq2YpkyrMed7ZhJMjcuNfGZtfsZFRhooscxiGkOcgD4",
//        publishedAt = "2024-07-09T12:34:56Z",
//        content = "This is the content of the sample news article $index.",
//        sourceName = "",
//        sourceId = ""
//    )
//}