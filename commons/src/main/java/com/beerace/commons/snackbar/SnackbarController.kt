package com.beerace.commons.snackbar

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * This composable is responsible for collecting requests from the SnackbarManager
 * and showing them using the SnackbarHostState.
 * */
@Composable
fun SnackbarController(
    snackbarHostState: SnackbarHostState,
    snackbarManager: SnackbarManager
) {
    val snackbarCommand by snackbarManager.commands.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(snackbarCommand) {
        snackbarCommand?.let {
            val job = launch {
                val result = snackbarHostState.showSnackbar(it.visual)

                if (result == SnackbarResult.ActionPerformed) {
                    it.onActionPerformed()
                }
            }

            if (it.visual.duration == SnackbarDuration.Indefinite) {
                delay(it.duration)
                job.cancel()
            }
        }
    }
}