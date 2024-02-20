package com.beerace.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.beerace.R
import com.beerace.commons.theme.BeeRaceTheme

@Composable
fun SplashNavigation() {
    hiltViewModel<SplashViewModel>()
    SplashScreen()
}

@Composable
private fun SplashScreen() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        LottieAnimation(
            composition = composition,
            restartOnPlay = false,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffff)
@Composable
private fun SplashScreenPreview() {
    BeeRaceTheme {
        SplashScreen()
    }
}