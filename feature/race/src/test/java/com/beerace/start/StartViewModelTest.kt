package com.beerace.start

import app.cash.turbine.test
import com.CoroutineExtension
import com.beerace.commons.navigation.NavigationManager
import com.beerace.commons.snackbar.SnackbarManager
import com.beerace.domain.timerace.GetTimeRaceUseCase
import com.beerace.domain.util.ErrorType
import com.beerace.domain.util.UseCaseResult
import com.beerace.start.model.StartEvent
import com.beerace.start.model.StartUiState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertIs

@ExtendWith(MockKExtension::class, CoroutineExtension::class)
internal class StartViewModelTest {
    @RelaxedMockK
    lateinit var navigationManager: NavigationManager

    @RelaxedMockK
    lateinit var snackbarManager: SnackbarManager

    @RelaxedMockK
    lateinit var getTimeRaceUseCase: GetTimeRaceUseCase

    private lateinit var viewModel: StartViewModel

    @BeforeEach
    internal fun setUp() {
        viewModel = StartViewModel(
            navigationManager = navigationManager,
            snackbarManager = snackbarManager,
            getTimeRaceUseCase = getTimeRaceUseCase
        )
    }

    @Test
    fun `handle should emit Loaded when item are returned`() = runTest {
        coEvery { getTimeRaceUseCase() } returns UseCaseResult.Success(mockk(relaxed = true))

        viewModel.state.test {
            awaitItem()
            viewModel.handle(StartEvent.StartClick)
            assertIs<StartUiState.Loading>(awaitItem())
            coVerify { getTimeRaceUseCase() }
            coVerify { navigationManager.navigate(any()) }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `handle should emit Error when error is returned`() = runTest {
        coEvery { getTimeRaceUseCase() } returns UseCaseResult.Error(ErrorType.GENERIC_ERROR)

        viewModel.state.test {
            awaitItem()
            viewModel.handle(StartEvent.StartClick)
            assertIs<StartUiState.Loading>(awaitItem())
            coVerify { getTimeRaceUseCase() }
            assertIs<StartUiState.Initial>(awaitItem())
            coVerify { snackbarManager.show(any()) }
            cancelAndIgnoreRemainingEvents()
        }
    }
}