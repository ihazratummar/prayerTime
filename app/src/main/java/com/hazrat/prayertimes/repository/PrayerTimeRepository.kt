//PrayerTimeRepository.kt

package com.hazrat.prayertimes.repository


import android.util.Log
import androidx.collection.longLongMapOf
import androidx.core.location.LocationManagerCompat.getCurrentLocation
import com.hazrat.prayertimes.data.LocationEntity
import com.hazrat.prayertimes.model.TimingsData
import com.hazrat.prayertimes.network.PrayerTimeApi
import com.hazrat.prayertimes.util.DateUtil
import com.hazrat.prayertimes.util.LocationHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class PrayerTimeRepository @Inject constructor(
    private val api: PrayerTimeApi,
    private val locationRepository: LocationRepository

){



    suspend fun getApiParameter(): TimingsData{

        val location: LocationEntity? = locationRepository.getLocation()
        val latitude = location?.latitude?: 0.0
        val longitude = location?.longitude?: 0.0
        Log.d("PrayerTimeRepository","$latitude $longitude")
        val currentDate = DateUtil.getCurrentDate()
        return api.getPrayerTimes(currentDate, "$latitude", "$longitude", 1).data
    }

}