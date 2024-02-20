package com.beerace.commons.navigation.direction

import androidx.navigation.NavOptions
import androidx.navigation.NavType.Companion.IntType
import androidx.navigation.navArgument
import com.beerace.commons.navigation.command.NavigationCommand
import com.beerace.commons.navigation.command.NavigationCommandWithArgs

object RaceDirections : DirectionWithArgs {
    const val ARG_RACE_TIME = "race_time"

    override val root: NavigationCommand = object : NavigationCommand {

        override val destination = "race"

        override val navOptions: NavOptions = NavOptions
            .Builder()
            .setPopUpTo(
                route = destination,
                inclusive = true
            )
            .build()
    }

    override fun forwardWithArgs(vararg args: String) = object : NavigationCommandWithArgs {

        override val route = "race?$ARG_RACE_TIME={$ARG_RACE_TIME}"

        override val arguments = listOf(
            navArgument(ARG_RACE_TIME) {
                type = IntType
                defaultValue = 0
            }
        )

        override val destination = run {
            val raceTime = args.getOrNull(0).orEmpty()
            route.replace("{$ARG_RACE_TIME}", raceTime)
        }

        override val navOptions: NavOptions = NavOptions.Builder().build()
    }
}