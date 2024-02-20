package com.beerace.commons.navigation

import com.beerace.commons.navigation.command.NavigationCommand
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationManager @Inject constructor() {
    private val _navActions: MutableSharedFlow<NavigationAction> = MutableSharedFlow(replay = 0)
    val navActions: SharedFlow<NavigationAction> = _navActions.asSharedFlow()

    suspend fun navigate(
        command: NavigationCommand
    ) {
        val action = NavigationAction(
            command = command
        )
        _navActions.emit(action)
    }
}