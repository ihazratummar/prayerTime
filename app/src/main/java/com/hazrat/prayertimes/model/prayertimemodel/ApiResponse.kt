package com.hazrat.prayertimes.model.prayertimemodel

data class ApiResponse(
    val code: Int,
    val `data`: List<Data>,
    val status: String
)