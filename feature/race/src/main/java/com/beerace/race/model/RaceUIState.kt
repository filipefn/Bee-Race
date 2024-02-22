package com.beerace.race.model

internal sealed class RaceUIState {

    data class Loaded(
        val time: String,
        val statusRaceList: List<StatusRaceUiModel>
    ) : RaceUIState()

    object Loading : RaceUIState()
    object WebViewError : RaceUIState()
    object Error : RaceUIState()
}

internal fun RaceUIState.checkIsNotErrorState() = this is RaceUIState.Error || this is RaceUIState.WebViewError