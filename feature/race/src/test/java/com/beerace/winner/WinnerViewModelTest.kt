package com.beerace.winner

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.CoroutineExtension
import com.beerace.commons.navigation.NavigationManager
import com.beerace.commons.navigation.direction.WinnerDirections
import com.beerace.commons.snackbar.SnackbarManager
import com.beerace.domain.timerace.GetTimeRaceUseCase
import com.beerace.domain.util.ErrorType
import com.beerace.domain.util.UseCaseResult
import com.beerace.winner.model.WinnerEvent
import com.beerace.winner.model.WinnerUiModel
import com.beerace.winner.model.WinnerUiState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertIs

@ExtendWith(MockKExtension::class, CoroutineExtension::class)
internal class WinnerViewModelTest {
    @RelaxedMockK
    lateinit var navigationManager: NavigationManager

    @RelaxedMockK
    lateinit var snackbarManager: SnackbarManager

    @RelaxedMockK
    lateinit var getTimeRaceUseCase: GetTimeRaceUseCase

    @RelaxedMockK
    lateinit var savedStateHandle: SavedStateHandle

    @BeforeEach
    internal fun setUp() {
        val winner = WinnerUiModel("name", "color")
        val winnerString = Json.encodeToString(winner)
        every { savedStateHandle.get<String>(WinnerDirections.ARG_WINNER_RACE) } returns winnerString
    }

    @Test
    fun `handle should emit Loaded when item are returned`() = runTest {
        coEvery { getTimeRaceUseCase() } returns UseCaseResult.Success(mockk(relaxed = true))

        val viewModel = createViewModel()

        viewModel.state.test {
            awaitItem()
            viewModel.handle(WinnerEvent.ReStartClick)
            assertIs<WinnerUiState.Loading>(awaitItem())
            coVerify { getTimeRaceUseCase() }
            coVerify { navigationManager.navigate(any()) }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `handle should emit Error when error is returned`() = runTest {
        coEvery { getTimeRaceUseCase() } returns UseCaseResult.Error(ErrorType.GENERIC_ERROR)

        val viewModel = createViewModel()

        viewModel.state.test {
            awaitItem()
            viewModel.handle(WinnerEvent.ReStartClick)
            assertIs<WinnerUiState.Loading>(awaitItem())
            coVerify { getTimeRaceUseCase() }
            assertIs<WinnerUiState.Loaded>(awaitItem())
            coVerify { snackbarManager.show(any()) }
            cancelAndIgnoreRemainingEvents()
        }
    }

    private fun createViewModel() = WinnerViewModel(
        navigationManager = navigationManager,
        snackbarManager = snackbarManager,
        getTimeRaceUseCase = getTimeRaceUseCase,
        savedStateHandle = savedStateHandle
    )
}