//PrayerTimeViewmodl.kt

package com.hazrat.prayertimes.screen


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hazrat.prayertimes.data.location.locationdetails.LocationDetailsEntity
import com.hazrat.prayertimes.data.prayerdetails.PrayerTimeEntity
import com.hazrat.prayertimes.repository.PrayerTimeRepository
import com.hazrat.prayertimes.repository.location.LocationNameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PrayerTimeViewModel @Inject constructor(
    private val repository: PrayerTimeRepository,
    private val locationNameRepository: LocationNameRepository
) : ViewModel() {


    private val _prayerTimes = MutableLiveData<List<PrayerTimeEntity>>()
    val prayerTimes: LiveData<List<PrayerTimeEntity>> get() = _prayerTimes

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error


    private val _locationName = MutableLiveData<String>()
    val locationName: LiveData<String> = _locationName

    fun fetchLocationName() {
        viewModelScope.launch {
            try {
                val response = locationNameRepository.getLocationDetails()
                val locationName = response?.village ?: response?.city ?: "Unknown"
                _locationName.value = locationName

                val locationEntity  = LocationDetailsEntity(
                    village = response?.village,
                    city = response?.city
                )
                locationNameRepository.saveLocation(locationEntity)

                Log.d("LocationName", "Location name fetched successfully: $locationName")
            } catch (e: Exception) {
                Log.e("LocationName", "Error fetching location name: ${e.message}")
            }
        }
    }

    fun getAllPrayerTimes() {
        viewModelScope.launch {
            try {
                val prayerTimesList = repository.getAllPrayer()
                _prayerTimes.value = prayerTimesList
            } catch (e: Exception) {
                _error.value = "Error fetching prayer times: ${e.message}"
            }
        }
    }

    init {
        viewModelScope.launch {
            locationNameRepository.getLocationName()
            repository.fetchAndSavePrayerTimesForMonth()
            getAllPrayerTimes()
            fetchLocationName()
            Log.d("GettingSomething", "${prayerTimes.value?.size}")
        }
    }
}