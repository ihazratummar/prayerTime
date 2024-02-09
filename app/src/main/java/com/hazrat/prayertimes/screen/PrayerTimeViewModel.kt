//PrayerTimeViewmodl.kt

package com.hazrat.prayertimes.screen


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hazrat.prayertimes.model.prayertimemodel.ApiResponse
import com.hazrat.prayertimes.repository.PrayerTimeRepository
import com.hazrat.prayertimes.repository.location.LocationNameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PrayerTimeViewModel @Inject constructor(
    private val repository: PrayerTimeRepository,
    private val locationNameRepository: LocationNameRepository
) : ViewModel() {


    private var _prayerTimes = MutableLiveData<ApiResponse>()
    val prayerTimes: LiveData<ApiResponse> get() = _prayerTimes


    private val _locationName = MutableLiveData<String>()
    val locationName: LiveData<String> = _locationName

    fun fetchLocationName() {
        viewModelScope.launch {
            try {
                val response = locationNameRepository.getLocationName()
                val locationName = response.address.village ?: response.address.city ?: "Unknown"
                _locationName.value = locationName
                Log.d("LocationName", "Location name fetched successfully: $locationName")
            } catch (e: Exception) {
                Log.e("LocationName", "Error fetching location name: ${e.message}")
            }
        }
    }

    fun fetchPrayerTimes() {
        viewModelScope.launch {
            val response = repository.getApiParameter()
            _prayerTimes.value = response
        }
    }

    init {
        fetchPrayerTimes()
        Log.d("GettingSomething", "${prayerTimes.value?.data}")
    }
}