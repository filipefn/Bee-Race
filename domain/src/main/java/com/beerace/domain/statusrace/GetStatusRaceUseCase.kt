package com.beerace.domain.statusrace

import com.beerace.domain.util.SuspendUseCaseInteractor
import com.beerace.domain.util.UseCaseResult
import javax.inject.Inject

class GetStatusRaceUseCase @Inject constructor(
    private val repository: StatusRaceRepository
) : SuspendUseCaseInteractor {

    suspend operator fun invoke(): UseCaseResult<List<StatusRace>> = run(repository.getStatusRace())
}