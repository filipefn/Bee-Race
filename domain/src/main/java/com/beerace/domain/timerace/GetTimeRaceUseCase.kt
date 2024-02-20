package com.beerace.domain.timerace

import com.beerace.domain.util.SuspendUseCaseInteractor
import com.beerace.domain.util.UseCaseResult
import javax.inject.Inject

class GetTimeRaceUseCase @Inject constructor(
    private val repository: TimeRaceRepository
) : SuspendUseCaseInteractor {

    suspend operator fun invoke(): UseCaseResult<TimeRace> = run(repository.getTimeRace())
}