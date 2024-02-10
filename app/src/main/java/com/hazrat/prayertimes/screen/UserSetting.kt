package com.hazrat.prayertimes.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hazrat.prayertimes.R
import com.hazrat.prayertimes.data.method.MethodEntity
import com.hazrat.prayertimes.data.prayerdetails.PrayerTimeEntity
import com.hazrat.prayertimes.model.prayertimemodel.Data
import com.hazrat.prayertimes.model.testmodel.MethodDetails
import com.hazrat.prayertimes.model.testmodel.SchoolDetails
import com.hazrat.prayertimes.navigation.Route
import com.hazrat.prayertimes.screen.component.MethodSelectionDialog
import com.hazrat.prayertimes.screen.component.PrayerTimeSettingCard
import com.hazrat.prayertimes.screen.component.SchoolSelectionDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserSetting(
    navController: NavController,
    settingViewModel: UserSettingViewModel = hiltViewModel(),
    prayerTimeViewModel: PrayerTimeViewModel = hiltViewModel()
) {

    fun openMethodSelectionDialog() {
        settingViewModel.openMethodSelectionDialog()
    }

    val prayerTimes by prayerTimeViewModel.prayerTimes.observeAsState()

    LazyColumn {
        item {
            TopBarWithBack(navController)
        }
        item {
            Divider()
        }
        item {
            val prayerTimeEntities = prayerTimes?.getOrNull(0)
            val methodName = prayerTimeEntities?.methodName
            if (methodName != null) {
                PrayerTimeSettingCard(
                    icon = R.drawable.athkar,
                    text = "Prayer Method",
                    subText = methodName,
                    onClick = {
                        openMethodSelectionDialog()
                    }
                )
            }else{
                PrayerTimeSettingCard(
                    icon = R.drawable.athkar,
                    text = "Prayer Method",
                    subText = null,
                    onClick = {
                        openMethodSelectionDialog()
                    }
                )
            }
        }
        item {
            PrayerTimeSettingCard(
                icon = R.drawable.duaicon,
                text = "Select Madhab",
                subText = settingViewModel.selectedSchool.name,
                onClick = {
                    settingViewModel.showSchoolSelectionDialog = true
                }
            )
        }
    }

    if (settingViewModel.showMethodSelectionDialog) {
        MethodSelectionDialog(
            showMethodSelectionDialog = settingViewModel.showMethodSelectionDialog,
            onMethodSelected = { method ->
                settingViewModel.selectedMethod = method
                settingViewModel.insertMethod(
                    MethodEntity(
                        settingViewModel.selectedMethod.method,
                        settingViewModel.selectedSchool.number
                    )
                )
                settingViewModel.showMethodSelectionDialog = false
            },
            onDismiss = { settingViewModel.showMethodSelectionDialog = false }
        )
    }

    if (settingViewModel.showSchoolSelectionDialog) {
        SchoolSelectionDialog(
            showSchoolSelectionDialog = settingViewModel.showSchoolSelectionDialog,
            onSchoolSelected = { school ->
                settingViewModel.selectedSchool = school
                settingViewModel.insertMethod(
                    MethodEntity(settingViewModel.selectedMethod.method, school.number)
                )
                settingViewModel.showSchoolSelectionDialog = false
            },
            onDismiss = { settingViewModel.showSchoolSelectionDialog = false }
        )
    }
}

@Composable
private fun TopBarWithBack(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(top = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(0.5f)
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .clickable {
                        navController.popBackStack()
                    }
                    .size(30.dp),
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Settings", style = TextStyle(
                    fontSize = 25.sp
                )
            )
        }
    }
}