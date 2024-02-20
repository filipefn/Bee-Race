package com.beerace.race.model

internal sealed interface RaceEvent {
    object OnCloseWebView : RaceEvent
}