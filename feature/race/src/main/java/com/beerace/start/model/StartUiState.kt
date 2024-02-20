package com.beerace.start.model

internal sealed class StartUiState {
    object Initial : StartUiState()

    object Loading : StartUiState()
}