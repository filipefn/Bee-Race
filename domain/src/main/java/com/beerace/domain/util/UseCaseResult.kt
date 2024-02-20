package com.beerace.domain.util

sealed class UseCaseResult<out D> {
    /**
     * The use case was ran successfully
     * @param data The data output
     */
    data class Success<D>(val data: D) : UseCaseResult<D>()

    /**
     * There was an error while performing the use case logic
     * @param error The error output
     */
    data class Error(val error: ErrorType) : UseCaseResult<Nothing>()
}

/**
 * Inline extension method to call over an RepositoryResult (typically the result of an useCase), passing two blocks:
 * an implementation to the success (That receive an parameter of type D and returns an UseCaseResult of types F to success)
 */
inline fun <D, F> RepositoryResult<D>.doOnResult(
    onSuccess: (D) -> UseCaseResult<F>,
    onError: (ErrorType) -> UseCaseResult<F>
): UseCaseResult<F> {
    return when (this) {
        is RepositoryResult.Success -> onSuccess(data)
        is RepositoryResult.Error -> onError(errorType)
    }
}

/**
 * Inline extension method to call over an UseCaseResult, passing two blocks:
 * an implementation to the success (That receive an parameter of type D and does not return anything)
 * and an implementation to the error(That receive an parameter of type ErrorResult and does not return anything)
 */
inline fun <D> UseCaseResult<D>.doOnResult(
    onSuccess: (D) -> Unit,
    onError: (ErrorType) -> Unit
) {
    when (this) {
        is UseCaseResult.Success -> onSuccess(data)
        is UseCaseResult.Error -> onError(error)
    }
}

suspend fun <A, B> UseCaseResult<A>.map(
    map: suspend (A) -> B
): UseCaseResult<B> {
    return when (this) {
        is UseCaseResult.Success -> UseCaseResult.Success(map(data))
        is UseCaseResult.Error -> this
    }
}