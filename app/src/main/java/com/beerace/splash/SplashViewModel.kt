package com.beerace.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beerace.commons.navigation.NavigationManager
import com.beerace.commons.navigation.direction.StartDirection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val navigationManager: NavigationManager
) : ViewModel() {

    init {
        val delay = 2000L

        CoroutineScope(Dispatchers.IO).launch {
            delay(delay)
            onNavigationToRace()
        }
    }

    private fun onNavigationToRace() {
        viewModelScope.launch {
            navigationManager.navigate(StartDirection.root)
        }
    }
}