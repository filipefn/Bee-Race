package com.beerace.network.adapter

import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.util.concurrent.TimeUnit.SECONDS

internal class ResultCall<T>(
    proxy: Call<T>
) : BaseCallWrapper<T, Result<T>>(proxy) {

    companion object {
        private const val CHECK_ROBOT = 403
        private const val TIME_TO_TIMEOUT_IN_SECONDS = 30L
    }

    override fun cloneImpl(): Call<Result<T>> = ResultCall(delegate.clone())

    override fun timeout(): Timeout = Timeout().timeout(TIME_TO_TIMEOUT_IN_SECONDS, SECONDS)

    override fun executeImpl(): Response<Result<T>> {
        val response = delegate.execute()
        val body = response.body()
        val code = response.code()

        val result = if (response.isSuccessful) {
            body.toResult()
        } else {
            if (code == CHECK_ROBOT) Result.failure(Failure.CheckRobot())
            else Result.failure(Failure.GenericError())
        }

        return Response.success(result)
    }

    override fun enqueueImpl(callback: Callback<Result<T>>) {
        val responseCallback = object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body = response.body()
                val code = response.code()

                val result = if (response.isSuccessful) {
                    body.toResult()
                } else {
                    if (code == CHECK_ROBOT) Result.failure(Failure.CheckRobot())
                    else Result.failure(Failure.GenericError())
                }

                callback.onResponse(
                    /* call = */ this@ResultCall,
                    /* response = */ Response.success(result)
                )
            }

            // It's always returning Generic error. Here we can handle, for example, errors in the request
            override fun onFailure(call: Call<T>, throwable: Throwable) {
                Timber.e(throwable)
                val exception = if (throwable is Failure.NetworkError) throwable else Failure.GenericError()
                val errorResponse = Result.failure<T>(exception)
                callback.onResponse(
                    /* call = */ this@ResultCall,
                    /* response = */ Response.success(errorResponse)
                )
            }
        }

        delegate.enqueue(responseCallback)
    }

    private fun T?.toResult(): Result<T> {
        return when {
            this != null -> Result.success(this)
            else -> runCatching {
                Result.success(Unit) as Result<T>
            }.getOrElse {
                Result.failure(Failure.GenericError())
            }
        }
    }
}
