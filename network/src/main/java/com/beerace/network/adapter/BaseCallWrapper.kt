package com.beerace.network.adapter

import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal abstract class BaseCallWrapper<T, R>(
    protected val delegate: Call<T>
) : Call<R> {

    override fun execute(): Response<R> = executeImpl()

    final override fun enqueue(callback: Callback<R>) = enqueueImpl(callback)

    final override fun clone(): Call<R> = cloneImpl()

    override fun cancel() = delegate.cancel()

    override fun request(): Request = delegate.request()

    override fun isExecuted() = delegate.isExecuted

    override fun isCanceled() = delegate.isCanceled

    abstract fun executeImpl(): Response<R>

    abstract fun enqueueImpl(callback: Callback<R>)

    abstract fun cloneImpl(): Call<R>
}
