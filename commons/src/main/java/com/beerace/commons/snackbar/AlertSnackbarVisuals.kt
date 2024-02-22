package com.beerace.commons.snackbar

import androidx.annotation.DrawableRes
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import com.beerace.commons.R
import com.beerace.commons.string.StringResource

internal const val DURATION_TIME_SHORT = 5000L

class AlertSnackbarVisuals(
    val messageId: StringResource,
    override val message: String = "",
    @DrawableRes val icon: Int? = R.drawable.ic_informational,
    override val actionLabel: String? = null,
    override val withDismissAction: Boolean = false
) : SnackbarVisuals {

    override val duration: SnackbarDuration = SnackbarDuration.Indefinite
}