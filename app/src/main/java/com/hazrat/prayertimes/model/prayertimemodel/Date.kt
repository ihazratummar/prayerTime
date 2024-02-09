package com.hazrat.prayertimes.model.prayertimemodel

data class Date(
    val gregorian: Gregorian,
    val hijri: Hijri,
    val readable: String,
    val timestamp: String
)