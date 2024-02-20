package com.beerace.domain.util

interface SuspendUseCaseInteractor {

    suspend fun <R> SuspendUseCaseInteractor.run(response: RepositoryResult<R>): UseCaseResult<R> =
        response.doOnResult(
            onSuccess = { UseCaseResult.Success(it) },
            onError = { UseCaseResult.Error(it) }
        )
}