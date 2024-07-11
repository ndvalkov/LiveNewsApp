package com.homeassignment.livenewsapp.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.homeassignment.livenewsapp.data.db.Article
import com.homeassignment.livenewsapp.ui.components.ListDetailPane

@Composable
fun HomeScreen(allArticles: List<Article>) {
    Surface(modifier = Modifier.fillMaxSize()) {
        ListDetailPane(articles = dummyArticles)
    }
}

val dummyArticles = List(20) { index ->
    Article(
        author = "Author $index",
        title = "Sample News Title $index",
        description = "This is a sample description of the news article $index. It should provide a brief summary of the content.",
        url = "https://www.ft.com/",
        urlToImage = "https://fastly.picsum.photos/id/1050/200/200.jpg?hmac=cq2YpkyrMed7ZhJMjcuNfGZtfsZFRhooscxiGkOcgD4",
        publishedAt = "2024-07-09T12:34:56Z",
        content = "This is the content of the sample news article $index.",
        sourceName = "",
        sourceId = ""
    )
}