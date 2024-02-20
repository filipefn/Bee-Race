package com.beerace.winner.model

internal sealed interface WinnerEvent {

    object ReStartClick : WinnerEvent
}