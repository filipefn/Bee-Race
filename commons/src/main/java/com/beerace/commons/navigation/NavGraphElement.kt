package com.beerace.commons.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavOptions

sealed interface NavGraphElement {

    val route: String
    val arguments: List<NamedNavArgument>

    data class NavGraphItem(
        override val route: String,
        override val arguments: List<NamedNavArgument> = emptyList(),
        val navOptions: NavOptions = NavOptions.Builder().build(),
        val content: @Composable (NavBackStackEntry, NavController) -> Unit
    ) : NavGraphElement
}