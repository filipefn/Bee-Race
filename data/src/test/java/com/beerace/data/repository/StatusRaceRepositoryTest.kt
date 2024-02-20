package com.beerace.data.repository

import com.beerace.data.api.ApiService
import com.beerace.data.dto.StatusRaceResponseDTO
import com.beerace.domain.statusrace.StatusRace
import com.beerace.domain.util.RepositoryResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertIs


@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
internal class StatusRaceRepositoryTest {

    @MockK
    private lateinit var mockApi: ApiService

    @InjectMockKs
    private lateinit var repository: StatusRaceRepositoryImpl

    @Test
    fun `when getStatusRace is called, it should call list of race`() = runTest {
        val dto = mockk<StatusRaceResponseDTO>(relaxed = true)
        coEvery { mockApi.getStatusRace() } returns Result.success(dto)

        val result = repository.getStatusRace()

        assertIs<RepositoryResult.Success<List<StatusRace>>>(result)
        coVerify { mockApi.getStatusRace() }
    }

    @Test
    fun `when API call fails, getStatusRace should propagate the error result`() = runTest {
        coEvery { mockApi.getStatusRace() } returns Result.failure(mockk())

        val result = repository.getStatusRace()

        assertIs<RepositoryResult.Error>(result)
    }
}