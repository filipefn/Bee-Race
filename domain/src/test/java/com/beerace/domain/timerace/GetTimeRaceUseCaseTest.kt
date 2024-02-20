package com.beerace.domain.timerace

import com.beerace.domain.util.ErrorType
import com.beerace.domain.util.RepositoryResult
import com.beerace.domain.util.UseCaseResult
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExtendWith(MockKExtension::class)
internal class GetTimeRaceUseCaseTest {

    @MockK
    private lateinit var repository: TimeRaceRepository

    @InjectMockKs
    lateinit var subject: GetTimeRaceUseCase

    @Test
    fun `invoke - GIVEN all success THEN return success`() = runBlocking {
        val timeRace = mockk<TimeRace>(relaxed = true)
        coEvery { repository.getTimeRace() } returns RepositoryResult.Success(timeRace)

        val result = subject()
        assertTrue(result is UseCaseResult.Success<TimeRace>)
        assertEquals(timeRace, result.data)
    }

    @Test
    fun `invoke - GIVEN get addresses failure THEN return error`() = runBlocking {
        coEvery { repository.getTimeRace() } returns RepositoryResult.Error(ErrorType.GENERIC_ERROR)

        val result = subject()
        assertTrue(result is UseCaseResult.Error)
    }
}