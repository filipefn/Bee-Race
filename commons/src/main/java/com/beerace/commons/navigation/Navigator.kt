package com.beerace.commons.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.beerace.commons.R
import com.beerace.commons.navigation.command.NavigationCommand
import com.beerace.commons.snackbar.AlertSnackbarVisuals
import com.beerace.commons.snackbar.SnackbarController
import com.beerace.commons.snackbar.SnackbarManager
import com.beerace.commons.string.StringResource
import com.beerace.network.util.ConnectivityObserver

@Composable
fun Navigator(
    navigationManager: NavigationManager,
    startDestination: String,
    snackbarManager: SnackbarManager,
    connectivityObserver: ConnectivityObserver,
    navGraph: NavGraph,
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val internetStatus by connectivityObserver.observe()
        .collectAsState(initial = ConnectivityObserver.ConnectivityStatus.Available)

    val snackbarHostState = remember { SnackbarHostState() }

    SnackbarController(snackbarHostState, snackbarManager)

    LaunchedEffect(Unit) {
        navigationManager.navActions.collect { action ->
            snackbarManager.forceDismiss()
            when (action.command) {
                is NavigationCommand -> {
                    if (navBackStackEntry?.lifecycleIsResumed() == true) {
                        navController.navigate(
                            route = action.command.destination,
                            navOptions = action.command.navOptions
                        )
                    }
                }
            }
        }
    }

    Scaffold(Modifier.background(MaterialTheme.colorScheme.primary)) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            NavHost(
                navController = navController,
                startDestination = startDestination
            ) {
                navGraph.items.forEach { element ->
                    when (element) {
                        is NavGraphElement.NavGraphItem -> element.buildComposable(
                            navGraphBuilder = this,
                            navController = navController
                        )
                    }
                }
            }
            if (internetStatus == ConnectivityObserver.ConnectivityStatus.Lost) {
                LaunchedEffect(Unit) {
                    snackbarManager.show(
                        visuals = AlertSnackbarVisuals(
                            message = StringResource.fromId(R.string.offline_error).toString()
                        )
                    )
                }
            }
        }
    }
}

private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED

fun NavGraphElement.NavGraphItem.buildComposable(
    navGraphBuilder: NavGraphBuilder,
    navController: NavController
) {
    navGraphBuilder.composable(
        route = route,
        arguments = arguments,
        content = { navBackStackEntry ->
            content(navBackStackEntry, navController)
        }
    )
}