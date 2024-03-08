package com.beerace.winner.factory

import com.beerace.race.model.StatusRaceUiModel
import com.beerace.winner.model.WinnerUiModel
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class WinnerUiFactory @Inject constructor() {

    suspend operator fun invoke(
        statusRace: StatusRaceUiModel
    ) = withContext(Default) {
        WinnerUiModel(name = statusRace.name, color = statusRace.color?.replace("#",""))
    }
}