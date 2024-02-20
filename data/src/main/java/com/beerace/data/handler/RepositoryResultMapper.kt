package com.beerace.data.handler

import com.beerace.domain.util.ErrorType
import com.beerace.domain.util.RepositoryResult
import com.beerace.network.adapter.Failure
import timber.log.Timber

fun <T> Result<T>.toRepositoryResult(): RepositoryResult<T> = map {
    RepositoryResult.Success(it)
}.getOrElse {
    Timber.e(it)
    RepositoryResult.Error(it.toErrorResult())
}

internal fun Throwable.toErrorResult() = when (this) {
    is Failure -> toErrorResult()
    else -> ErrorType.GENERIC_ERROR
}