package com.beerace.data.mapper

import com.beerace.data.dto.TimeRaceResponseDTO
import com.beerace.domain.timerace.TimeRace

internal fun TimeRaceResponseDTO.toDomain(): TimeRace = TimeRace(
    timeInSeconds = timeInSeconds
)
