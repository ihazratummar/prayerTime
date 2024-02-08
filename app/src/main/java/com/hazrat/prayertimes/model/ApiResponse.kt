package com.hazrat.prayertimes.model

data class ApiResponse(
    val code: Int,
    val data: TimingsData,
    val status: String
)