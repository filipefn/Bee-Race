package com.beerace.commons.snackbar

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals

internal const val DURATION_TIME_SHORT = 5000L
internal const val DURATION_TIME_LONG = 10000L

class AlertSnackbarVisuals(
    override val message: String = "",
    override val actionLabel: String? = null,
    override val withDismissAction: Boolean = false
) : SnackbarVisuals {

    override val duration: SnackbarDuration = SnackbarDuration.Indefinite
}