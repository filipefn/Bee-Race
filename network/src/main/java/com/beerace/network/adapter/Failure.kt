package com.beerace.network.adapter

sealed class Failure constructor(
    open val code: String = "",
    open val errorMessage: String = ""
) : Exception() {

    data class GenericError(
        override val code:  String = "",
        override val errorMessage:  String = ""
    ) : Failure()

    data class CheckRobot(
        override val code:  String = "403",
        override val errorMessage: String = ""
    ) : Failure()

    data class NetworkError(
        override val code: String ="10530",
        override val errorMessage: String = "No network connection Error"
    ) : Failure()
}
