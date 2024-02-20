package com.beerace.navigation


import com.beerace.commons.navigation.NavGraph
import com.beerace.commons.navigation.NavGraphElement
import com.beerace.commons.navigation.direction.RaceDirections
import com.beerace.commons.navigation.direction.SplashDirection
import com.beerace.commons.navigation.direction.StartDirection
import com.beerace.commons.navigation.direction.WinnerDirections
import com.beerace.race.RaceNavigation
import com.beerace.splash.SplashNavigation
import com.beerace.start.StartNavigation
import com.beerace.winner.WinnerNavigation
import javax.inject.Inject

internal class MainNavGraph @Inject constructor() : NavGraph {

    override val items: List<NavGraphElement> = listOf(
        NavGraphElement.NavGraphItem(
            route = SplashDirection.root.destination,
            navOptions = SplashDirection.root.navOptions
        ) { _, _ ->
            SplashNavigation()
        },
        NavGraphElement.NavGraphItem(
            route = StartDirection.root.destination,
            navOptions = StartDirection.root.navOptions
        ) { _, _ ->
            StartNavigation()
        },
        NavGraphElement.NavGraphItem(
            route = RaceDirections.forwardWithArgs().route,
            arguments = RaceDirections.forwardWithArgs().arguments,
            navOptions = RaceDirections.forwardWithArgs().navOptions
        ) { _, _ ->
            RaceNavigation()
        },

        NavGraphElement.NavGraphItem(
            route = WinnerDirections.forwardWithArgs().route,
            arguments = WinnerDirections.forwardWithArgs().arguments,
            navOptions = WinnerDirections.forwardWithArgs().navOptions
        ) { _, _ ->
            WinnerNavigation()
        },
    )
}