package com.homeassignment.livenewsapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.homeassignment.livenewsapp.data.db.Article

@Composable
fun ArticleListItem(article: Article,
                    onItemClick: (Article) -> Unit,
                    onFavoriteClick: ((String) -> Unit)? = null,
                    isFavorite: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(article) }
            .padding(4.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(4.dp)
    ) {
        article.urlToImage?.let { imageUrl ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "News image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(128.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        Text(
            text = article.title,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "By ${article.author ?: "Unknown"}",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
                Text(
                    text = "Published at: ${article.publishedAt}",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
            }

            onFavoriteClick?.let {
                IconButton(onClick = { onFavoriteClick(article.title) }) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                        contentDescription = null,
                        tint = if (isFavorite) Color.Blue else Color.Gray
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = article.description ?: "",
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleListItemPreview() {
    val dummyArticle = Article(
        author = "John Doe",
        title = "Sample News Title",
        description = "This is a sample description of the news article. It should provide a brief summary of the content.",
        url = "https://www.example.com",
        urlToImage = "https://picsum.photos/200",
        publishedAt = "2024-07-09 12:34:56",
        content = "This is the content of the sample news article. This is the content of the sample news article. ",
        sourceName = "",
        sourceId = ""
    )

    ArticleListItem(
        article = dummyArticle,
        onItemClick = {},
        onFavoriteClick = {},
        isFavorite = false
    )
}