package com.beerace.winner.factory

import com.beerace.race.model.StatusRaceUiModel
import com.beerace.winner.model.WinnerUiModel
import javax.inject.Inject

internal class WinnerUiFactory @Inject constructor() {

    operator fun invoke(
        statusRace: StatusRaceUiModel
    ) = with(statusRace) {
        WinnerUiModel(name = name, color = color?.replace("#",""))
    }
}