package com.hazrat.prayertimes.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Surface
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hazrat.prayertimes.model.testmodel.MethodDetails
import com.hazrat.prayertimes.model.testmodel.prayerMethods
import com.hazrat.prayertimes.ui.theme.PrayerTimesTheme


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MethodSelectionDialog(
    showMethodSelectionDialog: Boolean,
    onMethodSelected: (MethodDetails) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheetLayout(
        sheetContent = {
            Surface(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .nestedScroll(rememberNestedScrollInteropConnection()),
                color = Color.Transparent
            ) {
                LazyColumn(){
                    item{
                        Text(
                            text = "PRAYER TIMES",
                            style = TextStyle(fontSize = 10.sp),
                            color = Color(0xFFFFFFFF),
                            modifier = Modifier.padding(top = 30.dp)
                        )
                    }
                    items(prayerMethods.size) {index ->
                        val method = prayerMethods[index]
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp)
                                .clickable {
                                    onMethodSelected(method)
                                    onDismiss()
                                },
                            colors = CardDefaults.cardColors(Color.Transparent),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Row(modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Text(
                                    text = method.name,
                                    color = Color.White,
                                    modifier = Modifier.padding(start = 10.dp)
                                )
                            }
                        }
                    }
                }
            }
        },
        modifier = Modifier.fillMaxHeight(),
        sheetState = rememberModalBottomSheetState(
            initialValue = if (showMethodSelectionDialog) ModalBottomSheetValue.Expanded else ModalBottomSheetValue.Hidden
        ),
        sheetShape = MaterialTheme.shapes.large,
        sheetBackgroundColor = Color(0xff031600) ,
        sheetContentColor = MaterialTheme.colorScheme.surface
    ) {
        // Empty content
    }
}

@Preview
@Composable
fun MethodSelectionDialogPreview() {
    PrayerTimesTheme {
        MethodSelectionDialog(
            showMethodSelectionDialog = true,
            onMethodSelected = { methodDetails ->
                // Define what happens when a method is selected in the preview
            },
            onDismiss = {
                // Define what happens when the dialog is dismissed in the preview
            }
        )
    }
}