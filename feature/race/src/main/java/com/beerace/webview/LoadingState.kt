package com.beerace.webview

internal sealed interface LoadingState {

    object Initializing : LoadingState

    data class Loading(val progress: Float) : LoadingState

    object Finished : LoadingState
}