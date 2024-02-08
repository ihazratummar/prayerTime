package com.hazrat.prayertimes.model

data class Date(
    val gregorian: Gregorian,
    val hijri: Hijri,
    val readable: String,
    val timestamp: String
)