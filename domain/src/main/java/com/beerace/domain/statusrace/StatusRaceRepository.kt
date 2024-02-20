package com.beerace.domain.statusrace

import com.beerace.domain.util.RepositoryResult

interface StatusRaceRepository {
    suspend fun getStatusRace(): RepositoryResult<List<StatusRace>>
}
