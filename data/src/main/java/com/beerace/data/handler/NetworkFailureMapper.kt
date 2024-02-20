package com.beerace.data.handler

import com.beerace.domain.util.ErrorType
import com.beerace.network.adapter.Failure

fun Failure.toErrorResult() =
    when (this) {
        is Failure.GenericError -> ErrorType.GENERIC_ERROR
        is Failure.NetworkError -> ErrorType.NETWORK
        is Failure.CheckRobot -> ErrorType.CHECK_ROBOT
    }