package com.hazrat.prayertimes.screen

import android.provider.ContactsContract.Data
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hazrat.prayertimes.data.method.MethodEntity
import com.hazrat.prayertimes.data.prayerdetails.PrayerTimeEntity
import com.hazrat.prayertimes.model.testmodel.MethodDetails
import com.hazrat.prayertimes.model.testmodel.SchoolDetails
import com.hazrat.prayertimes.repository.MethodRepository
import com.hazrat.prayertimes.repository.PrayerTimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserSettingViewModel @Inject constructor(
    private val repository: MethodRepository,
    private val prayerTimeRepository: PrayerTimeRepository
) : ViewModel() {

    private val _methodList = MutableStateFlow<List<MethodEntity>>(emptyList())
    val methodList = _methodList.asStateFlow()

    private val _methodDetails = MutableStateFlow<List<MethodDetails>>(emptyList())
    val methodDetails = _methodDetails.asStateFlow()

    var showMethodSelectionDialog by mutableStateOf(false)
    var showSchoolSelectionDialog by  mutableStateOf(false)
    var selectedMethod by mutableStateOf(MethodDetails(1, ""))
    var selectedSchool by mutableStateOf(SchoolDetails(0, ""))
    fun openMethodSelectionDialog() {
        showMethodSelectionDialog = true
    }
    fun openSchoolSelectionDialog() {
        showSchoolSelectionDialog = true
    }


    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMethod().distinctUntilChanged()
                .collect { listOfMethod ->
                    if (listOfMethod.isEmpty()) {
                        Log.d("MethodChecking", "Empty List")
                    } else {
                        _methodList.value = listOfMethod
                    }
                }
        }
    }

    fun insertMethod(methodEntity: MethodEntity) = viewModelScope.launch {
        repository.deleteAllMethod()
        repository.insertMethod(methodEntity)
    }

    fun insertMethod(method: Int, school: Int?) {
        val methodEntity = MethodEntity(method = method, school = school)
        insertMethod(methodEntity)
    }
    fun updateMethod(methodEntity: MethodEntity) = viewModelScope.launch { repository.updateMethod(methodEntity) }

    fun deleteMethod(methodEntity: MethodEntity) = viewModelScope.launch { repository.deleteMethod(methodEntity) }
    fun deleteAllMethod() = viewModelScope.launch { repository.deleteAllMethod() }

    fun selectMethod(methodEntity: MethodEntity) {
        // Update the repository with the selected method
        insertMethod(methodEntity)
    }

    suspend fun deleteAlPrayer() = viewModelScope.launch { prayerTimeRepository.deleteAllPrayer() }


}