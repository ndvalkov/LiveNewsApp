package com.homeassignment.livenewsapp.ui.components

import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
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
            }
        },
        update = { webView ->
            webView.loadUrl(url)
        }
    )
}

class CustomWebViewClient() : WebViewClient() {

    var onPageFinishedCallback: (() -> Unit)? = null

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        onPageFinishedCallback?.invoke()
    }
}