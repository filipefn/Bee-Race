package com.beerace.data.repository

import com.beerace.data.api.ApiService
import com.beerace.data.handler.toRepositoryResult
import com.beerace.data.mapper.toDomain
import com.beerace.domain.timerace.TimeRace
import com.beerace.domain.timerace.TimeRaceRepository
import com.beerace.domain.util.RepositoryResult
import javax.inject.Inject

internal class TimeRaceRepositoryImpl @Inject constructor(
    private val api: ApiService
) : TimeRaceRepository {

    override suspend fun getTimeRace(): RepositoryResult<TimeRace> =
        api.getTimeRace()
            .mapCatching { it.toDomain() }
            .toRepositoryResult()
}
