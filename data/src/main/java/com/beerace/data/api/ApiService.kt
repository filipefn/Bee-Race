package com.beerace.data.api

import com.beerace.data.dto.StatusRaceResponseDTO
import com.beerace.data.dto.TimeRaceResponseDTO
import retrofit2.http.GET


internal interface ApiService {
    @GET("/bees/race/status")
    suspend fun getStatusRace(): Result<StatusRaceResponseDTO>

    @GET("/bees/race/duration")
    suspend fun getTimeRace(): Result<TimeRaceResponseDTO>
}
