package com.hazrat.prayertimes.model



data class TimingsData(val timings: Timings, val dates: Date)

data class Timings(
    val Asr: String,
    val Dhuhr: String,
    val Fajr: String,
    val Firstthird: String,
    val Imsak: String,
    val Isha: String,
    val Lastthird: String,
    val Maghrib: String,
    val Midnight: String,
    val Sunrise: String,
    val Sunset: String
)