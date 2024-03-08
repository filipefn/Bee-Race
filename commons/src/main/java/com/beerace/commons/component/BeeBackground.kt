package com.beerace.commons.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun BeeBackgroundComponent(icon: Int, contentDescription: String, backgroundColor: Color, modifier: Modifier = Modifier) {
    Box(
        modifier
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = contentDescription
        )
    }
}

@Preview
@Composable
private fun BeeBackgroundComponentPreview() {
    MaterialTheme {
        BeeBackgroundComponent(
            android.R.drawable.ic_dialog_info, "", Color.Red
        )
    }
}