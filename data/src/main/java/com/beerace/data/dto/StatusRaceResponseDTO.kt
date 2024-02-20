package com.beerace.data.dto

internal data class StatusRaceResponseDTO(
    val beeList: List<StatusRaceItemResponseDTO>
)

internal data class StatusRaceItemResponseDTO(
    val name: String,
    val color: String
)