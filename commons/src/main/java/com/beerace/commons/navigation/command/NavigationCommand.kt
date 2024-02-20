package com.beerace.commons.navigation.command

import androidx.navigation.NavOptions

interface NavigationCommand {

    val destination: String

    val navOptions: NavOptions
}