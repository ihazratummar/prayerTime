//PrayerTimeRepository.kt

package com.hazrat.prayertimes.repository


import android.util.Log
import com.hazrat.prayertimes.data.location.coordinents.LocationEntity
import com.hazrat.prayertimes.model.prayertimemodel.ApiResponse
import com.hazrat.prayertimes.network.PrayerTimeApi
import com.hazrat.prayertimes.repository.location.LocationRepository
import com.hazrat.prayertimes.util.DateUtil
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class PrayerTimeRepository @Inject constructor(
    private val api: PrayerTimeApi,
    private val locationRepository: LocationRepository,
    private val methodRepository: MethodRepository

){

    suspend fun getApiParameter(): ApiResponse {
        val location: LocationEntity? = locationRepository.getLocation()
        val latitude = location?.latitude?: 24.628
        val longitude = location?.longitude?: 88.011
        val methodList = methodRepository.getMethod().firstOrNull() ?: emptyList()
        val methodValue = methodList.firstOrNull()?.method ?: 1 // Default value is 1 if methodList or method is null
        Log.d("PrayerTimeRepository","$latitude $longitude $methodValue")
        val currentDate = DateUtil.getCurrentDate()
        val year = DateUtil.getCurrentYear()
        val month = DateUtil.getCurrentMonth()
        return api.getPrayerTimes(year, month, "$latitude", "$longitude", methodValue)
    }

}