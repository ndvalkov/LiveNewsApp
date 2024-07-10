package com.homeassignment.livenewsapp.data.remote

data class NewsResponseDto(
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleDto>,
    val code: String? = null,
    val message: String? = null
)


