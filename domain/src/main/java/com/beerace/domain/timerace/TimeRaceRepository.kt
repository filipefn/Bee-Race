package com.beerace.domain.timerace

import com.beerace.domain.util.RepositoryResult

interface TimeRaceRepository {
    suspend fun getTimeRace(): RepositoryResult<TimeRace>
}
