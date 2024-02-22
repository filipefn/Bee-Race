package com.beerace.commons.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration.Companion.Underline
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.beerace.commons.snackbar.AlertSnackbarVisuals
import com.beerace.commons.string.toString

@Composable
fun AlertSnackbarComponent(visuals: AlertSnackbarVisuals, onClickAction: () -> Unit = {}) {
    val context = LocalContext.current

    AlertSnackbar(
        message = visuals.message.ifBlank { visuals.messageId.toString(context) },
        actionLabel = visuals.actionLabel,
        icon = visuals.icon,
        onClickAction = onClickAction
    )
}

@Composable
fun AlertSnackbar(
    message: String,
    actionLabel: String? = null,
    @DrawableRes icon: Int? = null,
    onClickAction: () -> Unit = {}
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = MaterialTheme.colorScheme.errorContainer)
            .clickable {
                onClickAction()
            }
            .padding(
                horizontal = 8.dp,
                vertical = 16.dp
            )
    ) {
        val (iconRef, textRef) = createRefs()
        icon?.let {
            Icon(
                painter = painterResource(id = it),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .constrainAs(iconRef) {
                        top.linkTo(parent.top)
                        end.linkTo(
                            anchor = textRef.start,
                            margin = 8.dp
                        )
                    }
            )
        }

        Text(
            text = buildAnnotatedString {
                append(message)
                actionLabel?.let {
                    append(" ")
                    withStyle(SpanStyle(textDecoration = Underline)) {
                        append(it)
                    }
                }
            },
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier
                .constrainAs(textRef) {
                    centerTo(parent)
                },
            overflow = Ellipsis,
        )
    }
}

@Preview
@Composable
private fun NotificationSnackbarPreview() {
    MaterialTheme {
        AlertSnackbar(
            "Error"
        )
    }
}