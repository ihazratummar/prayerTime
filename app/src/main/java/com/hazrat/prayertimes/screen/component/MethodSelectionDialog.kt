package com.hazrat.prayertimes.screen.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hazrat.prayertimes.model.testmodel.MethodDetails
import com.hazrat.prayertimes.model.testmodel.prayerMethods

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MethodSelectionDialog(
    showMethodSelectionDialog: Boolean,
    onMethodSelected: (MethodDetails) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheetLayout(
        sheetContent = {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Select Prayer Calculation Method:")
                Divider()
                prayerMethods.forEach { method ->
                    Text(
                        text = method.name,
                        modifier = Modifier.clickable {
                            onMethodSelected(method)
                            onDismiss()
                        }
                    )
                }
            }
        },
        sheetState = rememberModalBottomSheetState(
            initialValue = if (showMethodSelectionDialog) ModalBottomSheetValue.Expanded else ModalBottomSheetValue.Hidden
        ),
        sheetShape = MaterialTheme.shapes.large,
        sheetContentColor = MaterialTheme.colorScheme.surface
    ) {
        // Empty content
    }
}