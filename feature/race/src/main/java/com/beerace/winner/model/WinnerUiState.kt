package com.beerace.winner.model

internal sealed class WinnerUiState {
    data class Loaded(val winner: WinnerUiModel) : WinnerUiState()

    object Loading : WinnerUiState()
}