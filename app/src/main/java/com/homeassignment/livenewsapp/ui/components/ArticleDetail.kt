package com.homeassignment.livenewsapp.ui.components

import androidx.compose.runtime.Composable

@Composable
fun ArticleDetail(sourceUrl: String) {
    WebView(sourceUrl)
}