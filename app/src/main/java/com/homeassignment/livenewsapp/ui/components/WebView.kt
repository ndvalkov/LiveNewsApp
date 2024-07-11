package com.homeassignment.livenewsapp.ui.components

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebView(
    url: String,
    webViewClient: WebViewClient = CustomWebViewClient()
) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                this.webViewClient = webViewClient
                this.settings.useWideViewPort = true
            }
        },
        update = { webView ->
            webView.loadUrl(url)
        },
        modifier = Modifier.padding(8.dp) // bug fix flicker
    )
}

@Stable
class CustomWebViewClient() : WebViewClient() {

    var onPageFinishedCallback: (() -> Unit)? = null

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        onPageFinishedCallback?.invoke()
    }
}