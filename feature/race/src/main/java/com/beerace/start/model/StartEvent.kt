package com.beerace.start.model

internal sealed interface StartEvent {
    object StartClick : StartEvent
}