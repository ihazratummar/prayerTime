//PrayerTimeRepository.kt

package com.hazrat.prayertimes.repository


import android.util.Log
import com.hazrat.prayertimes.data.LocationEntity
import com.hazrat.prayertimes.model.TimingsData
import com.hazrat.prayertimes.model.testmodel.MethodDetails
import com.hazrat.prayertimes.network.PrayerTimeApi
import com.hazrat.prayertimes.screen.UserSettingViewModel
import com.hazrat.prayertimes.util.DateUtil
import kotlinx.coroutines.flow.firstOrNull
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
        val methodList = methodRepository.getMethod().firstOrNull() ?: emptyList()
        val methodValue = methodList.firstOrNull()?.method ?: 1 // Default value is 1 if methodList or method is null
        Log.d("PrayerTimeRepository","$latitude $longitude $methodValue")
        val currentDate = DateUtil.getCurrentDate()
        return api.getPrayerTimes(currentDate, "$latitude", "$longitude", methodValue).data
    }

}