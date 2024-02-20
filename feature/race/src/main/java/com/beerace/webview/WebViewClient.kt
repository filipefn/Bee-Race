package com.beerace.webview

import android.webkit.WebView
import android.webkit.WebViewClient

internal class WebViewClient : WebViewClient() {

    lateinit var state: WebViewState

    override fun onPageFinished(
        view: WebView,
        url: String
    ) {
        super.onPageFinished(view, url)
        state.loadingState = LoadingState.Finished
    }
}