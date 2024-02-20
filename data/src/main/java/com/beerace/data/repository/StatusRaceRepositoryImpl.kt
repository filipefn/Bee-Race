package com.beerace.data.repository

import com.beerace.data.api.ApiService
import com.beerace.data.handler.toRepositoryResult
import com.beerace.data.mapper.toDomain
import com.beerace.domain.statusrace.StatusRace
import com.beerace.domain.statusrace.StatusRaceRepository
import com.beerace.domain.util.RepositoryResult
import javax.inject.Inject

internal class StatusRaceRepositoryImpl @Inject constructor(
    private val api: ApiService
) : StatusRaceRepository {

    override suspend fun getStatusRace(): RepositoryResult<List<StatusRace>> =
        api.getStatusRace()
            .mapCatching { it.toDomain() }
            .toRepositoryResult()
}
