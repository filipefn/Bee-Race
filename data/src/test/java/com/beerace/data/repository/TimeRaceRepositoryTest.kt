package com.beerace.data.repository

import com.beerace.data.api.ApiService
import com.beerace.data.dto.TimeRaceResponseDTO
import com.beerace.domain.timerace.TimeRace
import com.beerace.domain.util.RepositoryResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertIs


@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
internal class TimeRaceRepositoryTest {

    @MockK
    private lateinit var mockApi: ApiService

    @InjectMockKs
    private lateinit var repository: TimeRaceRepositoryImpl

    @Test
    fun `when getTimeRace is called, it should call time reminding`() = runBlocking {
        val dto = mockk<TimeRaceResponseDTO>(relaxed = true)
        coEvery { mockApi.getTimeRace() } returns Result.success(dto)

        val result = repository.getTimeRace()

        assertIs<RepositoryResult.Success<List<TimeRace>>>(result)
        coVerify { mockApi.getTimeRace() }
    }

    @Test
    fun `when API call fails, getTimeRace should propagate the error result`() = runBlocking {
        coEvery { mockApi.getTimeRace() } returns Result.failure(mockk())

        val result = repository.getTimeRace()

        assertIs<RepositoryResult.Error>(result)
    }
}