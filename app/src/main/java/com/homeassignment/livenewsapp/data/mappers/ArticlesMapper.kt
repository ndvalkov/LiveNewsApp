package com.homeassignment.livenewsapp.data.mappers

import com.homeassignment.livenewsapp.data.db.Article
import com.homeassignment.livenewsapp.data.remote.ArticleDto
import com.homeassignment.livenewsapp.data.remote.SourceDto

object ArticleMapper {

    fun fromDto(dto: ArticleDto): Article {
        return Article(
            sourceId = dto.source.id,
            sourceName = dto.source.name,
            author = dto.author,
            title = dto.title,
            description = dto.description,
            url = dto.url,
            urlToImage = dto.urlToImage,
            publishedAt = dto.publishedAt,
            content = dto.content
        )
    }

    fun toDto(article: Article): ArticleDto {
        return ArticleDto(
            source = SourceDto(
                id = article.sourceId,
                name = article.sourceName
            ),
            author = article.author,
            title = article.title,
            description = article.description,
            url = article.url,
            urlToImage = article.urlToImage,
            publishedAt = article.publishedAt,
            content = article.content
        )
    }
}