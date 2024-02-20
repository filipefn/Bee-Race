package com.beerace.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beerace.commons.navigation.NavigationManager
import com.beerace.commons.navigation.direction.RaceDirections
import com.beerace.commons.snackbar.AlertSnackbarVisuals
import com.beerace.commons.snackbar.SnackbarManager
import com.beerace.domain.timerace.GetTimeRaceUseCase
import com.beerace.domain.util.doOnResult
import com.beerace.domain.util.map
import com.beerace.start.model.StartEvent
import com.beerace.start.model.StartUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class StartViewModel @Inject constructor(
    private val getTimeRaceUseCase: GetTimeRaceUseCase,
    private val navigationManager: NavigationManager,
    private val snackbarManager: SnackbarManager,
) : ViewModel() {

    private val _state = MutableStateFlow<StartUiState>(StartUiState.Initial)
    val state = _state.asStateFlow()

    fun handle(event: StartEvent) {
        if (event is StartEvent.StartClick) fetchTimeRace()
    }

    private fun fetchTimeRace() {
        _state.value = StartUiState.Loading
        viewModelScope.launch {
            getTimeRaceUseCase()
                .map { it.timeInSeconds.toString() }
                .doOnResult(
                    onSuccess = { moveForwardToRace(it) },
                    onError = {
                        _state.value = StartUiState.Initial
                        snackbarManager.show(AlertSnackbarVisuals("Something went Wrong"))
                    }
                )
        }
    }

    private suspend fun moveForwardToRace(time: String) {
        navigationManager.navigate(RaceDirections.forwardWithArgs(time))
    }
}