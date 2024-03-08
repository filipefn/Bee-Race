package com.beerace.winner

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beerace.commons.navigation.NavigationManager
import com.beerace.commons.navigation.direction.RaceDirections
import com.beerace.commons.navigation.direction.WinnerDirections.ARG_WINNER_RACE
import com.beerace.commons.snackbar.AlertSnackbarVisuals
import com.beerace.commons.snackbar.SnackbarManager
import com.beerace.commons.string.StringResource
import com.beerace.domain.timerace.GetTimeRaceUseCase
import com.beerace.domain.util.doOnResult
import com.beerace.domain.util.map
import com.beerace.race.R
import com.beerace.winner.model.WinnerEvent
import com.beerace.winner.model.WinnerUiModel
import com.beerace.winner.model.WinnerUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
internal class WinnerViewModel @Inject constructor(
    private val getTimeRaceUseCase: GetTimeRaceUseCase,
    private val navigationManager: NavigationManager,
    private val snackbarManager: SnackbarManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow<WinnerUiState>(WinnerUiState.Loading)
    val state = _state.asStateFlow()

    private val winnerString = savedStateHandle.get<String>(ARG_WINNER_RACE).orEmpty()
    private lateinit var winner: WinnerUiModel

    init {
        setUpWinner()
    }

    fun handle(event: WinnerEvent) {
        if (event is WinnerEvent.ReStartClick) fetchTimeRace()
    }

    private fun setUpWinner() {
        winner = Json.decodeFromString(winnerString)
        _state.value = WinnerUiState.Loaded(winner)
    }

    private fun fetchTimeRace() {
        _state.value = WinnerUiState.Loading
        viewModelScope.launch {
            getTimeRaceUseCase()
                .map { it.timeInSeconds.toString() }
                .doOnResult(
                    onSuccess = { moveForwardToRace(it) },
                    onError = {
                        _state.value = WinnerUiState.Loaded(winner)
                        snackbarManager.show(AlertSnackbarVisuals(StringResource.fromId(R.string.something_went_wrong)))
                    }
                )
        }
    }

    private suspend fun moveForwardToRace(time: String) {
        navigationManager.navigate(RaceDirections.forwardWithArgs(time))
    }
}