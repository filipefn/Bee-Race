package com.beerace.commons.snackbar

import androidx.compose.material3.SnackbarVisuals

class SnackbarCommand(
    val visual: SnackbarVisuals,
    val duration: Long,
    val onActionPerformed: () -> Unit = {}
)