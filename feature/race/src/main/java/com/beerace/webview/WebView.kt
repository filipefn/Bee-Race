package com.beerace.webview

import android.annotation.SuppressLint
import android.net.Uri
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.webkit.WebView
import android.widget.FrameLayout
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.beerace.commons.component.LoaderComponent

@SuppressLint("SetJavaScriptEnabled")
@Composable
internal fun WebView(
    state: WebViewState,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
    client: WebViewClient = remember { WebViewClient() },
    chromeClient: WebChromeClient = remember { WebChromeClient() }
) {
    BoxWithConstraints(modifier) {
        val isInitialLoading = remember { mutableStateOf(true) }

        val width = if (constraints.hasFixedWidth) MATCH_PARENT else WRAP_CONTENT
        val height = if (constraints.hasFixedHeight) MATCH_PARENT else WRAP_CONTENT
        val layoutParams = FrameLayout.LayoutParams(width, height)

        val webView = state.webView

        BackHandler(enabled = true) {
            onClose()
        }

        webView?.let {
            LaunchedEffect(it, state) {
                snapshotFlow { state.content }.collect { content ->
                    when (content) {
                        is WebContent.Url -> {
                            val uri = runCatching { Uri.parse(content.url) }.getOrNull()
                            val url = uri?.buildUpon()?.build().toString()
                            it.loadUrl(url)
                        }

                        is WebContent.Post -> {
                            it.postUrl(
                                content.url,
                                content.data
                            )
                        }

                        WebContent.NavigatorOnly -> Unit
                    }
                }
            }

            LaunchedEffect(it, state) {
                snapshotFlow { state.loadingState }.collect { loadingState ->
                    isInitialLoading.value = loadingState !is LoadingState.Finished
                }
            }
        }

        client.state = state
        chromeClient.state = state

        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    this.layoutParams = layoutParams

                    state.viewState?.let {
                        this.restoreState(it)
                    }

                    webChromeClient = chromeClient
                    webViewClient = client

                    settings.javaScriptEnabled = true
                }.also {
                    state.webView = it
                }
            },
            modifier = modifier
        )

        // Show loading dots while WebView is loading initial content
        if (isInitialLoading.value) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LoaderComponent()
            }
        }
    }
}