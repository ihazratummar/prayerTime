package com.hazrat.prayertimes.screen


import android.util.Log
import android.view.View
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.viewpager2.widget.ViewPager2
import com.hazrat.prayertimes.data.prayerdetails.PrayerTimeEntity
import com.hazrat.prayertimes.model.prayertimemodel.Data
import com.hazrat.prayertimes.navigation.Route
import com.hazrat.prayertimes.screen.component.DisplayTimeUntilPrayer
import com.hazrat.prayertimes.screen.component.getTime
import com.hazrat.prayertimes.util.DateUtil
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun PrayerTimer(viewModel: PrayerTimeViewModel = hiltViewModel(), navController: NavController) {
    ShowData(viewModel, navController)

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowData(viewModel: PrayerTimeViewModel = hiltViewModel(), navController: NavController) {

    val context  = LocalContext.current

    val locationName by remember { viewModel.locationName }.observeAsState()

    val prayerTimes by remember { viewModel.prayerTimes }.observeAsState()

//    LaunchedEffect(viewModel.prayerTimes) {
//        viewModel.fetchPrayerTimes()
//
//    }
    LaunchedEffect(Unit){
        viewModel.fetchLocationName()
        locationName?.let { Log.d("LocationName", it) }
    }

    Scaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar(title = { Text(text = "$locationName")},
                actions = {
                    Icon(imageVector = Icons.Default.Settings,
                        contentDescription = "Setting Icon",
                        modifier = Modifier.clickable {
                            navController.navigate(Route.UserSettings.route)

                        })
                }

            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            prayerTimes?.let {times ->
                ViewPager(prayerTimes = times)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ViewPager(prayerTimes: List<PrayerTimeEntity>) {

    val todayDay = DateUtil.getCurrentDay()
    val todayIndex = prayerTimes.indexOfFirst { data ->
        val day = data.day.toInt()
        todayDay == day
    }
    val initialPage = if (todayIndex != -1 ) todayIndex else todayDay -1 // Use 0 as the default page if today's date is not found

    val pagerState = rememberPagerState(
        pageCount = { prayerTimes.size },
        initialPage = initialPage
    )

    LaunchedEffect(pagerState) {
        // Collect from the a snapshotFlow reading the currentPage
        snapshotFlow { pagerState.currentPage }.collect { page ->
            // Do something with each page change, for example:
            // viewModel.sendPageSelectedEvent(page)
            Log.d("Page change", "Page changed to $page")
        }
    }

    HorizontalPager(
        state = pagerState,
    ) { page ->
        PrayerTimesDay(prayerTimes[page])
    }
}


@Composable
fun PrayerTimesDay(data: PrayerTimeEntity) {

    Log.d("PrayerTimesDay", "Readable Date: ${data.readableDate}")
    Log.d("PrayerTimesDay", "Hijri: ${data.hijriMonthEn} ${data.hijriDay},${data.hijriYear}")
    Log.d("PrayerTimesDay", "Method Name: ${data.methodName}")
    Log.d("PrayerTimesDay", "Fajr Time: ${getTime(data.fajrTime)}")
    Log.d("PrayerTimesDay", "Dhuhr Time: ${getTime(data.dhuhrTime)}")
    Log.d("PrayerTimesDay", "Asr Time: ${getTime(data.asrTime)}")
    Log.d("PrayerTimesDay", "Maghrib Time: ${getTime(data.maghribTime)}")
    Log.d("PrayerTimesDay", "Isha Time: ${getTime(data.ishaTime)}")

    Column {

        Text(text = data.readableDate)
        Text(text = "Hijri: ${data.hijriMonthEn} ${data.hijriDay},${data.hijriYear} ")
        data.methodName?.let { Text(text = it) }
        Text(text = "Fajr: ${getTime(data.fajrTime)}")
        Text(text = "Dhuhr: ${getTime(data.dhuhrTime)}")
        Text(text = "Asr: ${getTime(data.asrTime)}")
        Text(text = "Maghrib: ${getTime(data.maghribTime)}")
        Text(text = "Isha: ${getTime(data.ishaTime)}")
        DisplayTimeUntilPrayer(data)

    }

}







