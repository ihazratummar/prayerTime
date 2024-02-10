package com.hazrat.prayertimes.screen.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.hazrat.prayertimes.model.testmodel.MethodDetails
import com.hazrat.prayertimes.model.testmodel.SchoolDetails

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SchoolSelectionDialog(
    showSchoolSelectionDialog: Boolean,
    onSchoolSelected: (SchoolDetails) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheetLayout(
        sheetContent = {
            Column() {
                Text(text = "Hello")
            }
        },
        sheetState = rememberModalBottomSheetState(
            initialValue = if (showSchoolSelectionDialog) ModalBottomSheetValue.Expanded else ModalBottomSheetValue.Hidden
        ),
        sheetShape = MaterialTheme.shapes.large,
        sheetBackgroundColor = MaterialTheme.colorScheme.onBackground,
        sheetContentColor = MaterialTheme.colorScheme.surface
    ) {

    }
}