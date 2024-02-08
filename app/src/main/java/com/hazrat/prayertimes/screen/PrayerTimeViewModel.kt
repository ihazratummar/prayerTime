//PrayerTimeViewmodl.kt

package com.hazrat.prayertimes.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hazrat.prayertimes.model.Timings
import com.hazrat.prayertimes.repository.PrayerTimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PrayerTimeViewModel @Inject constructor(
    private val repository: PrayerTimeRepository,
) : ViewModel() {

    private val _timings =MutableLiveData<Timings>()
    val timings: LiveData<Timings> get() = _timings


    init {
        viewModelScope.launch {
            _timings.value  = repository.getApiParameter().timings
        }
    }

}