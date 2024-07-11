package com.homeassignment.livenewsapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ArticleDetail(sourceUrl: String, title: String?) {
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {

        var lastUrl by remember { mutableStateOf(sourceUrl) }
        var isLoading by remember { mutableStateOf(true) }

        val webViewClient = remember {
            CustomWebViewClient().apply {
                onPageFinishedCallback = {
                    isLoading = false
                }
            }
        }

        WebView(lastUrl, webViewClient)
        if(isLoading) {
            CircularProgressIndicator()
        }
        
        Text(text = "Article id: $title")
    }
}