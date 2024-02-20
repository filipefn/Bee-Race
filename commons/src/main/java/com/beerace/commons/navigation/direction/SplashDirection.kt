package com.beerace.commons.navigation.direction

import androidx.navigation.NavOptions
import com.beerace.commons.navigation.command.NavigationCommand

object SplashDirection : Direction {
    override val root: NavigationCommand = object : NavigationCommand {

        override val destination = "splash"

        override val navOptions: NavOptions = NavOptions
            .Builder()
            .setPopUpTo(
                route = destination,
                inclusive = true
            )
            .build()
    }
}