package com.beerace.commons.navigation.command

import androidx.navigation.NamedNavArgument

interface NavigationCommandWithArgs : NavigationCommand {

    val route: String

    val arguments: List<NamedNavArgument>
}