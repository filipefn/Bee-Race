package com.beerace.race

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.CoroutineExtension
import com.beerace.commons.navigation.NavigationManager
import com.beerace.commons.navigation.direction.RaceDirections
import com.beerace.commons.snackbar.SnackbarManager
import com.beerace.domain.statusrace.GetStatusRaceUseCase
import com.beerace.domain.statusrace.StatusRace
import com.beerace.domain.util.ErrorType
import com.beerace.domain.util.UseCaseResult
import com.beerace.race.factory.RaceUiFactory
import com.beerace.race.model.PositionEnum
import com.beerace.race.model.RaceEvent
import com.beerace.race.model.RaceUIState
import com.beerace.race.model.StatusRaceUiModel
import com.beerace.winner.factory.WinnerUiFactory
import com.beerace.winner.model.WinnerUiModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertIs

@ExtendWith(MockKExtension::class, CoroutineExtension::class)
internal class RaceViewModelTest {
    @RelaxedMockK
    lateinit var navigationManager: NavigationManager

    @RelaxedMockK
    lateinit var snackbarManager: SnackbarManager

    @RelaxedMockK
    lateinit var getStatusRaceUseCase: GetStatusRaceUseCase

    @RelaxedMockK
    lateinit var raceUIFactory: RaceUiFactory

    @RelaxedMockK
    lateinit var winnerUiFactoryFactory: WinnerUiFactory

    @RelaxedMockK
    lateinit var savedStateHandle: SavedStateHandle

    @BeforeEach
    internal fun setUp() {
        every { savedStateHandle.get<Int>(RaceDirections.ARG_RACE_TIME) } returns 2
    }

    @Test
    fun `handle should emit Loaded when item are returned`() = runTest {
        val status = listOf(StatusRace("name", "color"))
        val statusUiModel = StatusRaceUiModel("name", PositionEnum.FIRST_PLACE,"color")
        coEvery { getStatusRaceUseCase() } returns UseCaseResult.Success(status)
        coEvery { raceUIFactory(status) } returns listOf(statusUiModel)
        coEvery { winnerUiFactoryFactory(statusUiModel) } returns WinnerUiModel("name", "color")

        val viewModel = createViewModel()

        viewModel.state.test {
            assertIs<RaceUIState.Loading>(awaitItem())
            assertIs<RaceUIState.Loaded>(awaitItem())
            awaitItem()
            coVerify { navigationManager.navigate(any()) }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `handle should emit Error when error is returned`() = runTest {
        coEvery { getStatusRaceUseCase() } returns UseCaseResult.Error(ErrorType.GENERIC_ERROR)

        val viewModel = createViewModel()

        viewModel.state.test {
            assertIs<RaceUIState.Loading>(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `handle should emit Robot error when error is returned`() = runTest {
        coEvery { getStatusRaceUseCase() } returns UseCaseResult.Error(ErrorType.CHECK_ROBOT)

        val viewModel = createViewModel()

        viewModel.state.test {
            assertIs<RaceUIState.Loading>(awaitItem())
            assertIs<RaceUIState.WebViewError>(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `handle should when item are returned`() = runTest {
        val status = listOf(StatusRace("name", "color"))
        val statusUiModel = StatusRaceUiModel("name", PositionEnum.FIRST_PLACE,"color")
        coEvery { getStatusRaceUseCase() } returns UseCaseResult.Success(status)
        coEvery { raceUIFactory(status) } returns listOf(statusUiModel)
        coEvery { winnerUiFactoryFactory(statusUiModel) } returns WinnerUiModel("name", "color")

        val viewModel = createViewModel()

        viewModel.state.test {
            viewModel.handle(RaceEvent.OnCloseWebView)
            assertIs<RaceUIState.Loading>(awaitItem())
            assertIs<RaceUIState.Loaded>(awaitItem())
            awaitItem()
            coVerify { navigationManager.navigate(any()) }
            cancelAndIgnoreRemainingEvents()
        }
    }

    private fun createViewModel() = RaceViewModel(
        navigationManager = navigationManager,
        snackbarManager = snackbarManager,
        getStatusRaceUseCase = getStatusRaceUseCase,
        raceUIFactory = raceUIFactory,
        winnerUIFactory = winnerUiFactoryFactory,
        savedStateHandle = savedStateHandle
    )
}