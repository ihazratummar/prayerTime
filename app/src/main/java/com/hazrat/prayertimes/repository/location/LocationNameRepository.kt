package com.hazrat.prayertimes.repository.location

import android.util.Log
import com.hazrat.prayertimes.data.location.coordinents.LocationEntity
import com.hazrat.prayertimes.data.location.locationdetails.LocationDetailsEntity
import com.hazrat.prayertimes.data.location.locationdetails.LocationNameDao
import com.hazrat.prayertimes.model.locationmodel.LocationNameFinder
import com.hazrat.prayertimes.network.LocationNameApi
import javax.inject.Inject

class LocationNameRepository @Inject constructor(
    private val locationNameApi: LocationNameApi,
    private val locationRepository: LocationRepository,
    private val locationNameDao: LocationNameDao
) {
    suspend fun getLocationName(): LocationNameFinder {

        val location:LocationEntity? =  locationRepository.getLocation()
        val lat = location?.latitude?: 24.628
        val lon = location?.longitude?: 88.011
        Log.d("getLocationName", "$lat $lon")
        return locationNameApi.getLocationName(format = "json", lat = lat, lon = lon)
    }


    suspend fun getLocationDetails(): LocationDetailsEntity? {
        return locationNameDao.getLocationDetails()
    }

    private suspend fun deleteOtherLocationDetails() {
        locationNameDao.deleteOtherLocationDetails()
    }

    suspend fun saveLocation(locationDetailsEntity: LocationDetailsEntity) {
         locationNameDao.saveLocationDetails(locationDetailsEntity)
        deleteOtherLocationDetails()
    }
}