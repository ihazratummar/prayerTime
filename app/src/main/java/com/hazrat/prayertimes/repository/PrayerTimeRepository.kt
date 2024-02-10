//PrayerTimeRepository.kt

package com.hazrat.prayertimes.repository


import android.util.Log
import com.hazrat.prayertimes.data.location.coordinents.LocationEntity
import com.hazrat.prayertimes.data.prayerdetails.PrayerTimeDao
import com.hazrat.prayertimes.data.prayerdetails.PrayerTimeEntity
import com.hazrat.prayertimes.model.prayertimemodel.ApiResponse
import com.hazrat.prayertimes.model.prayertimemodel.Data
import com.hazrat.prayertimes.network.PrayerTimeApi
import com.hazrat.prayertimes.repository.location.LocationRepository
import com.hazrat.prayertimes.util.DateUtil
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class PrayerTimeRepository @Inject constructor(
    private val api: PrayerTimeApi,
    private val locationRepository: LocationRepository,
    private val methodRepository: MethodRepository,
    private val prayerTimeDao: PrayerTimeDao

){



    private suspend fun getApiParameterForMonth(): ApiResponse {
        val location: LocationEntity? = locationRepository.getLocation()
        val latitude = location?.latitude?: 24.628
        val longitude = location?.longitude?: 88.011
        val methodList = methodRepository.getMethod().firstOrNull() ?: emptyList()
        val methodValue = methodList.firstOrNull()?.method ?: 1 // Default value is 1 if methodList or method is null
        Log.d("PrayerTimeRepository","$latitude $longitude $methodValue")
        val currentDate = DateUtil.getCurrentDate()
        val year = DateUtil.getCurrentYear()
        val month = DateUtil.getCurrentMonth()
        val apiResponse = api.getPrayerTimes(year, month, "$latitude", "$longitude", methodValue)
        Log.d("PrayerTimeRepository", "API response: $apiResponse")
        return apiResponse
    }


    private fun convertApiResponseToEntity(apiResponse: Data): PrayerTimeEntity {
        val timings = apiResponse.timings
        val date = apiResponse.date
        val meta = apiResponse.meta
        return PrayerTimeEntity(
            day = date.gregorian.day.toInt(),
            fajrTime = timings.Fajr,
            sunriseTime = timings.Sunrise,
            dhuhrTime = timings.Dhuhr,
            asrTime = timings.Asr,
            sunsetTime = timings.Sunset,
            maghribTime = timings.Maghrib,
            ishaTime = timings.Isha,
            imsakTime = timings.Imsak,
            midnightTime = timings.Midnight,
            firstThirdTime = timings.Firstthird,
            lastThirdTime = timings.Lastthird,
            readableDate = date.readable,
            gregorianDate = date.gregorian.date,
            gregorianDay = date.gregorian.day,
            gregorianWeekday = date.gregorian.weekday.en,
            gregorianMonthNum = date.gregorian.month.number,
            gregorianMonthName =date.gregorian.month.en,
            gregorianYear = date.gregorian.year,
            hijriDate = date.hijri.date,
            hijriDay =date.hijri.day,
            hijriWeekdayEn = date.hijri.weekday.en,
            hijriWeekdayEr = date.hijri.weekday.ar,
            hijriMonthAr = date.hijri.month.ar,
            hijriMonthEn = date.hijri.month.en,
            hijriMonthNumber = date.hijri.month.number,
            hijriYear = date.hijri.year,
            timezone = meta.timezone,
            methodId = meta.method.id,
            methodName = meta.method.name,
            methodFajrParam = meta.method.params.Fajr,
            methodIshaParam = meta.method.params.Isha,
            latitudeAdjustmentMethod = meta.latitudeAdjustmentMethod,
            midnightMode = meta.midnightMode,
            school = meta.school

        ).apply {
            Log.d("PrayerTimeRepository", "Converted entity: $this")
        }

    }

    suspend fun fetchAndSavePrayerTimesForMonth(): List<PrayerTimeEntity> {
        val apiResponse = getApiParameterForMonth()
        val prayerTimesList = mutableListOf<PrayerTimeEntity>()
        for (apiDataForDay in apiResponse.data) {
            val prayerTimeEntity = convertApiResponseToEntity(apiDataForDay)

            // Check if the entity for this day already exists in the database
            val existingEntity = prayerTimeDao.getPrayerTimeByDay(prayerTimeEntity.day)
            if (existingEntity == null) {
                // If it doesn't exist, insert the entity into the database
                prayerTimeDao.insertAllPrayerTimes(listOf(prayerTimeEntity))
                Log.d("Insertion", "Inserting prayer time entity: $prayerTimeEntity")
                prayerTimesList.add(prayerTimeEntity)
            } else {
                // If it already exists, you may want to handle this case accordingly
                Log.d("Insertion", "Prayer time entity for day ${prayerTimeEntity.day} already exists")
            }
        }
        Log.d("Insertion", "Prayer time entities inserted successfully")
        return prayerTimesList
    }


    suspend fun insertAllPrayerTimes(prayerTimes: List<PrayerTimeEntity>): List<PrayerTimeEntity> {
        prayerTimeDao.insertAllPrayerTimes(prayerTimes)
        return prayerTimes
    }
    suspend fun getAllPrayer() = prayerTimeDao.getAllPrayer()
    suspend fun deletePrayerTime(prayerTimeEntity: List<PrayerTimeEntity>) = prayerTimeDao.deletePrayerTime(prayerTimeEntity)

    suspend fun deleteAllPrayer() = prayerTimeDao.deleteAllPrayer()

}