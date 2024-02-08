package com.hazrat.prayertimes.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.hazrat.prayertimes.repository.LocationRepository
import com.hazrat.prayertimes.screen.PrayerTimer
import com.hazrat.prayertimes.ui.theme.PrayerTimesTheme
import com.hazrat.prayertimes.util.LocationHandler
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var locationRepository: LocationRepository
    private lateinit var locationHandler: LocationHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationHandler = LocationHandler(this, locationRepository)
        setContent {
            PrayerTimesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PrayerTimer()
                }
            }
        }
    }
}
