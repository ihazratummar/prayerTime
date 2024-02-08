package com.hazrat.prayertimes.navigation

import androidx.navigation.NamedNavArgument

sealed class Route (
    val route: String,
    val argument: List<NamedNavArgument> = emptyList()
){
    object PrayerTimeScreen:Route(route = "PrayerTimeScreen")

    object UserSettings:Route(route = "UserSettingScreen")

}