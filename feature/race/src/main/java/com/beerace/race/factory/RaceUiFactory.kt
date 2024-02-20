package com.beerace.race.factory

import com.beerace.domain.statusrace.StatusRace
import com.beerace.race.model.PositionEnum
import com.beerace.race.model.StatusRaceUiModel
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.withContext
import javax.inject.Inject


internal class RaceUiFactory @Inject constructor() {

    suspend operator fun invoke(
        statusRace: List<StatusRace>,
    ) = withContext(Default) {
        statusRace.mapIndexed { index, item ->
            StatusRaceUiModel(
                name = item.name,
                position = PositionEnum.getPositionEnum(index),
                color = item.color,

            )
        }
    }
}