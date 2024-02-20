package com.beerace.winner.model

import kotlinx.serialization.Serializable

@Serializable
data class WinnerUiModel(
    val name: String,
    val color: String?
)