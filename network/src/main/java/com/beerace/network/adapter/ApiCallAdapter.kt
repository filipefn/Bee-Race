package com.beerace.network.adapter

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

internal class ApiCallAdapter<R> internal constructor(
    private val responseType: Type
) : CallAdapter<R, Any> {

    override fun responseType(): Type = responseType

    override fun adapt(call: Call<R>): ResultCall<R> = ResultCall(call)
}