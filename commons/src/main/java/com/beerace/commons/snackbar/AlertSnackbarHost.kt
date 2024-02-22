package com.beerace.commons.snackbar

import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import com.beerace.commons.component.AlertSnackbarComponent

@Composable
fun AlertSnackbarHost(snackbarHostState: SnackbarHostState) {
    SnackbarHost(
        hostState = snackbarHostState,
        snackbar = {
            val visuals = it.visuals as? AlertSnackbarVisuals

            if (visuals != null) {
                AlertSnackbarComponent(
                    visuals = visuals,
                    onClickAction = { it.performAction() }
                )
            } else {
                Snackbar(it)
            }
        }
    )
}