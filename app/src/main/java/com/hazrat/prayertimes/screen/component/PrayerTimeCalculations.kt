package com.hazrat.prayertimes.screen.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.hazrat.prayertimes.data.prayerdetails.PrayerTimeEntity
import com.hazrat.prayertimes.model.prayertimemodel.Data
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@Composable
fun DisplayTimeUntilPrayer(data: PrayerTimeEntity) {
    val currentTime = LocalTime.now()

    val prayerTimes = listOf(
        "Fajr" to data.fajrTime,
        "Dhuhr" to data.dhuhrTime,
        "Asr" to data.asrTime,
        "Maghrib" to data.maghribTime,
        "Isha" to data.ishaTime
    )

    var nextPrayerTime: LocalTime? = null
    var nextPrayerName: String? = null

    prayerTimes.forEach { (prayerName, prayerTime) ->
        val parsedPrayerTime = parsePrayerTime(prayerTime)
        if (parsedPrayerTime != null && currentTime.isBefore(parsedPrayerTime)) {
            if (nextPrayerTime == null || parsedPrayerTime.isBefore(nextPrayerTime)) {
                nextPrayerTime = parsedPrayerTime
                nextPrayerName = prayerName
            }
        }
    }

    if (nextPrayerTime != null && nextPrayerName != null) {
        val difference = calculateTimeDifference(currentTime, nextPrayerTime!!)
        val timeUntilNextPrayer =
            "Time until $nextPrayerName: ${difference.hour} hours ${difference.minute} minutes"
        Text(text = timeUntilNextPrayer)
    } else {
        Text(text = "No upcoming prayers.")
    }
}

private fun parsePrayerTime(prayerTime: String): LocalTime? {
    return try {
        LocalTime.parse(prayerTime.substring(0, 5), DateTimeFormatter.ofPattern("HH:mm"))
    } catch (e: Exception) {
        null
    }
}

private fun calculateTimeDifference(currentTime: LocalTime, nextPrayerTime: LocalTime): LocalTime {
    return nextPrayerTime.minusHours(currentTime.hour.toLong()).minusMinutes(currentTime.minute.toLong())
}

fun getTime(prayerTime: String): String {
    return prayerTime.substring(0, 5) // Extract HH:mm part
}
