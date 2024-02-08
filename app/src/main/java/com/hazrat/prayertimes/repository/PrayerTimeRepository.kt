//PrayerTimeRepository.kt

package com.hazrat.prayertimes.repository


import android.util.Log
import com.hazrat.prayertimes.data.LocationEntity
import com.hazrat.prayertimes.model.TimingsData
import com.hazrat.prayertimes.model.testmodel.MethodDetails
import com.hazrat.prayertimes.network.PrayerTimeApi
import com.hazrat.prayertimes.screen.UserSettingViewModel
import com.hazrat.prayertimes.util.DateUtil
import javax.inject.Inject

class PrayerTimeRepository @Inject constructor(
    private val api: PrayerTimeApi,
    private val locationRepository: LocationRepository,
    private val methodRepository: MethodRepository

){


    suspend fun getApiParameter(): TimingsData{
        val location: LocationEntity? = locationRepository.getLocation()
        val latitude = location?.latitude?: 0.0
        val longitude = location?.longitude?: 0.0
        val method = methodRepository.getMethod()
        Log.d("PrayerTimeRepository","$latitude $longitude $method")
        val currentDate = DateUtil.getCurrentDate()
        return api.getPrayerTimes(currentDate, "$latitude", "$longitude", 1).data
    }

}