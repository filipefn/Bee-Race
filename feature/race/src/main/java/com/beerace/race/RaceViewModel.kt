package com.beerace.race

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beerace.commons.navigation.NavigationManager
import com.beerace.commons.navigation.direction.RaceDirections.ARG_RACE_TIME
import com.beerace.commons.navigation.direction.WinnerDirections
import com.beerace.commons.snackbar.AlertSnackbarVisuals
import com.beerace.commons.snackbar.SnackbarManager
import com.beerace.commons.string.StringResource
import com.beerace.domain.statusrace.GetStatusRaceUseCase
import com.beerace.domain.util.ErrorType
import com.beerace.domain.util.doOnResult
import com.beerace.domain.util.map
import com.beerace.race.factory.RaceUiFactory
import com.beerace.race.model.RaceEvent
import com.beerace.race.model.RaceUIState
import com.beerace.race.model.StatusRaceUiModel
import com.beerace.race.model.checkIsNotErrorState
import com.beerace.winner.factory.WinnerUiFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
internal class RaceViewModel @Inject constructor(
    private val raceUIFactory: RaceUiFactory,
    private val getStatusRaceUseCase: GetStatusRaceUseCase,
    private val winnerUIFactory: WinnerUiFactory,
    private val snackbarManager: SnackbarManager,
    private val navigationManager: NavigationManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val DURATION_TIME_LONG = 20000L
    private val _state = MutableStateFlow<RaceUIState>(RaceUIState.Loading)
    val state = _state.asStateFlow()

    private var remainingSeconds = savedStateHandle.get<Int>(ARG_RACE_TIME) ?: 0

    init {
        start()
    }

    fun handle(event: RaceEvent) {
        if (event is RaceEvent.OnCloseWebView) {
            returnToRace()
        }
    }

    private fun start() {
        viewModelScope.launch {
            while (remainingSeconds > 0) {
                if (_state.value.checkIsNotErrorState().not()) {
                    delay(1000)
                    remainingSeconds--
                    fetchStatusRace()
                } else {
                    awaitCancellation()
                }
            }
            if (remainingSeconds == 0 && state.value is RaceUIState.Loaded) {
                moveForwardToWinner((state.value as RaceUIState.Loaded).statusRaceList.first())
            }
        }
    }

    private fun formatTime(): String {
        val minutes = (remainingSeconds / 60).toString().padStart(2, '0')
        val seconds = (remainingSeconds % 60).toString().padStart(2, '0')
        return "$minutes:$seconds"
    }

    private suspend fun fetchStatusRace() {
        getStatusRaceUseCase.invoke()
            .map { raceUIFactory(it) }
            .doOnResult(
                onSuccess = { _state.value = RaceUIState.Loaded(formatTime(), it) },
                onError = {
                    _state.value = RaceUIState.Error
                    snackbarManager.show(AlertSnackbarVisuals(
                        messageId = StringResource.fromId(R.string.race_something_went_wrong)
                    ),
                        duration = DURATION_TIME_LONG,
                        onActionPerformed = { returnToRace() }
                    )
                }
            )
    }

    private fun returnToRace() {
        _state.value = RaceUIState.Loading
        start()
    }

    private suspend fun moveForwardToWinner(statusRaceUiModel: StatusRaceUiModel) {
        navigationManager.navigate(
            WinnerDirections.forwardWithArgs(
                Json.encodeToString(
                    winnerUIFactory(statusRaceUiModel)
                )
            )
        )
    }
}