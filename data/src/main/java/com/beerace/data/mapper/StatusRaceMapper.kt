package com.beerace.data.mapper

import com.beerace.data.dto.StatusRaceItemResponseDTO
import com.beerace.data.dto.StatusRaceResponseDTO
import com.beerace.domain.statusrace.StatusRace

internal fun StatusRaceResponseDTO.toDomain(): List<StatusRace> =
    beeList.map {
        it.toDomain()
    }

private fun StatusRaceItemResponseDTO.toDomain(): StatusRace = StatusRace(
    name = name,
    color = color
)
