package com.beerace.webview

import android.webkit.WebChromeClient
import android.webkit.WebView

internal class WebChromeClient : WebChromeClient() {

    companion object {
        private const val FULL_PROGRESS = 100F
    }

    lateinit var state: WebViewState

    override fun onProgressChanged(view: WebView, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
        if (state.loadingState == LoadingState.Finished) return
        state.loadingState = LoadingState.Loading(progress = newProgress / FULL_PROGRESS)
    }
}