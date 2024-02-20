package com.beerace.network.config

internal data class BeeRaceConstantsConfig(
    val timeout: Long = 95L,
    val cacheSize: Long = 10485760 // 10 * 1024 * 1024
)