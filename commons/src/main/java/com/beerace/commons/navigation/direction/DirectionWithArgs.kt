package com.beerace.commons.navigation.direction

import com.beerace.commons.navigation.command.NavigationCommandWithArgs

interface DirectionWithArgs : Direction {

    fun forwardWithArgs(vararg args: String): NavigationCommandWithArgs
}