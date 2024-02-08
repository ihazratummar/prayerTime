package com.hazrat.prayertimes.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hazrat.prayertimes.model.Timings
import com.hazrat.prayertimes.navigation.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrayerTimer(viewModel: PrayerTimeViewModel = hiltViewModel(), navController: NavController) {
    val timings by viewModel.timings.observeAsState()

    Scaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar(title = { Text(text = "PrayerTimes") },
                actions = {
                    Icon(imageVector = Icons.Default.Settings, contentDescription ="Settings",
                        modifier = Modifier.clickable {
                            navController.navigate(Route.UserSettings.route)
                        })
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            PrayerTimes(timings)
        }
    }

}

@Composable
private fun PrayerTimes(timings: Timings?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(fraction = 0.4f)
            .padding(10.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(Color.DarkGray)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            timings?.let { Text(text = it.Fajr) }
            timings?.let { Text(text = it.Dhuhr) }
            timings?.let { Text(text = it.Asr) }
            timings?.let { Text(text = it.Maghrib) }
            timings?.let { Text(text = it.Isha) }
        }
    }
}