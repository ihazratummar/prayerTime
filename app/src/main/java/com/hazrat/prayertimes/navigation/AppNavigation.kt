package com.hazrat.prayertimes.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hazrat.prayertimes.screen.PrayerTimeViewModel
import com.hazrat.prayertimes.screen.PrayerTimer
import com.hazrat.prayertimes.screen.UserSetting
import com.hazrat.prayertimes.screen.UserSettingViewModel

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.PrayerTimeScreen.route){
        composable(route = Route.PrayerTimeScreen.route){
            val viewModel:PrayerTimeViewModel = hiltViewModel()
            PrayerTimer(viewModel,navController)
        }
        composable(route = Route.UserSettings.route){
            val viewModel:UserSettingViewModel = hiltViewModel()
            UserSetting(navController = navController, viewModel)
        }
    }

}