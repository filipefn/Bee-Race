package com.beerace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.beerace.commons.navigation.NavGraph
import com.beerace.commons.navigation.NavigationManager
import com.beerace.commons.navigation.Navigator
import com.beerace.commons.navigation.direction.SplashDirection
import com.beerace.commons.snackbar.SnackbarManager
import com.beerace.commons.theme.BeeRaceTheme
import com.beerace.network.util.ConnectivityObserver
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigationManager: NavigationManager

    @Inject
    lateinit var navGraph: NavGraph

    @Inject
    lateinit var snackbarManager: SnackbarManager

    @Inject
    lateinit var connectivityObserver: ConnectivityObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BeeRaceTheme {
                Navigator(
                    navigationManager = navigationManager,
                    snackbarManager = snackbarManager,
                    connectivityObserver = connectivityObserver,
                    startDestination = SplashDirection.root.destination,
                    navGraph = navGraph)
            }
        }
    }
}