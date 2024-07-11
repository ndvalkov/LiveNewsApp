package com.homeassignment.livenewsapp.data.mappers

import com.homeassignment.livenewsapp.data.db.Article
import com.homeassignment.livenewsapp.data.remote.ArticleDto

fun ArticleDto.toArticle(): Article {
    return Article(
        sourceId = this.source.id,
        sourceName = this.source.name,
        author = this.author,
        title = this.title,
        description = this.description,
        url = this.url,
        urlToImage = this.urlToImage,
        publishedAt = this.publishedAt,
        content = this.content
    )
}
