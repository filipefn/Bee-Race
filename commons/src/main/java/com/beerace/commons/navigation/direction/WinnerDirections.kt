package com.beerace.commons.navigation.direction

import androidx.navigation.NavOptions
import androidx.navigation.NavType.Companion.StringType
import androidx.navigation.navArgument
import com.beerace.commons.navigation.command.NavigationCommand
import com.beerace.commons.navigation.command.NavigationCommandWithArgs

object WinnerDirections : DirectionWithArgs {
    const val ARG_WINNER_RACE = "winner_race"

    override val root: NavigationCommand = object : NavigationCommand {

        override val destination = "winner"

        override val navOptions: NavOptions = NavOptions
            .Builder()
            .setPopUpTo(
                route = destination,
                inclusive = true
            )
            .build()
    }

    override fun forwardWithArgs(vararg args: String) = object : NavigationCommandWithArgs {

        override val route = "race?$ARG_WINNER_RACE={$ARG_WINNER_RACE}"

        override val arguments = listOf(
            navArgument(ARG_WINNER_RACE) {
                type = StringType
                defaultValue = ""
            }
        )

        override val destination = run {
            val raceTime = args.getOrNull(0).orEmpty()
            route.replace("{$ARG_WINNER_RACE}", raceTime)
        }

        override val navOptions: NavOptions = NavOptions.Builder().build()
    }
}