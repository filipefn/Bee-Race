package com.beerace.commons.snackbar

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A Manager for keeping track of requests to show a snackbar.
 * */
@Singleton
class SnackbarManager @Inject constructor() {
    private val _commands: MutableSharedFlow<SnackbarCommand?> = MutableSharedFlow(0)
    val commands: SharedFlow<SnackbarCommand?> = _commands.asSharedFlow()

    suspend fun show(
        visuals: AlertSnackbarVisuals,
        duration: Long = DURATION_TIME_SHORT,
        onActionPerformed: () -> Unit = {}
    ) {
        _commands.emit(
            SnackbarCommand(
                visual = visuals,
                duration = duration,
                onActionPerformed = onActionPerformed
            )
        )
    }

    suspend fun forceDismiss() {
        _commands.emit(null)
    }
}