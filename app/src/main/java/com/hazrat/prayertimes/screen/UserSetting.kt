package com.hazrat.prayertimes.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hazrat.prayertimes.data.MethodEntity
import com.hazrat.prayertimes.model.testmodel.MethodDetails
import com.hazrat.prayertimes.screen.component.MethodSelectionDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserSetting(
    navController: NavController,
    settingViewModel: UserSettingViewModel = hiltViewModel()
) {
    val methodDetails by settingViewModel.methodDetails.collectAsState()

    var showMethodSelectionDialog by remember { mutableStateOf(false) }
    var selectedMethod by remember { mutableStateOf(MethodDetails(1, "")) }

    Scaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Settings") },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier
                            .clickable {
                                navController.popBackStack()
                            }
                    )
                }
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            // Display a button to open the method selection dialog
            Button(
                onClick = { showMethodSelectionDialog = true },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Select Method")
            }

            // Display selected method
            Text(text = "Selected Method: ${selectedMethod.name}")

            // Method selection dialog
            MethodSelectionDialog(
//                methods = methodDetails,
                showMethodSelectionDialog = showMethodSelectionDialog,
                onMethodSelected = { method ->
                    settingViewModel.deleteAllMethod()
                    selectedMethod = method
                    settingViewModel.insertMethod(MethodEntity(method.method))
                    showMethodSelectionDialog = false // Close the dialog after selection
                },
                onDismiss = { showMethodSelectionDialog = false }
            )
        }
    }
}