package com.beerace.domain.statusrace

import com.beerace.domain.util.ErrorType
import com.beerace.domain.util.RepositoryResult
import com.beerace.domain.util.UseCaseResult
import com.beerace.domain.util.doOnResult
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertIs

@ExtendWith(MockKExtension::class)
class GetStatusRaceUseCaseTest {
    @MockK
    private lateinit var repository: StatusRaceRepository

    @InjectMockKs
    lateinit var useCase: GetStatusRaceUseCase

    @Test
    fun `invoke - GIVEN all success THEN return success`() = runTest {
        val statusRace = listOf(StatusRace("name", "color"))
        coEvery { repository.getStatusRace() } returns RepositoryResult.Success(statusRace)

        val successAction = mockk<(List<StatusRace>) -> Unit>(relaxed = true)
        val errorAction = mockk<(ErrorType) -> Unit>(relaxed = true)

        val response = useCase()
        response.doOnResult(
            onSuccess = successAction,
            onError = errorAction
        )
        assertIs<UseCaseResult<List<StatusRace>>>(response)
        verify { successAction(statusRace) }
        verify(exactly = 0) { errorAction(any()) }
    }

    @Test
    fun `invoke - GIVEN get addresses failure THEN return error`() = runTest {
        val errorResult = ErrorType.GENERIC_ERROR
        coEvery {
            repository.getStatusRace()
        } returns RepositoryResult.Error(errorResult)

        val successAction = mockk<(List<StatusRace>) -> Unit>(relaxed = true)
        val errorAction = mockk<(ErrorType) -> Unit>(relaxed = true)

        val response = useCase()
        response.doOnResult(
            onSuccess = successAction,
            onError = errorAction
        )

        assertIs<UseCaseResult<ErrorType>>(response)
        verify(exactly = 0) { successAction(mockk()) }
        verify { errorAction(errorResult) }
    }
}