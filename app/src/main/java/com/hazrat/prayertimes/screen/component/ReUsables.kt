package com.hazrat.prayertimes.screen.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hazrat.prayertimes.R
import com.hazrat.prayertimes.ui.theme.PrayerTimesTheme


@Composable
fun PrayerTimeSettingCard(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    text: String,
    subText: String?,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = 4.dp,
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surface
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ){
            Icon(painter = painterResource(id = icon),
                contentDescription ="Icon",
                modifier = Modifier.size(30.dp))
            Column(
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = text,
                    style = MaterialTheme.typography.bodyLarge)
                if (subText != null) {
                    Text(text = subText,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.LightGray)
                }
            }
        }
    }
}


@Preview
@Composable
fun ClickableCardPreview() {
    PrayerTimesTheme {
        PrayerTimeSettingCard(
            icon = R.drawable.athkar,
            text = "Go Back",
            subText = "Prayermethod",
            onClick = {
                // Handle onClick action here
            }
        )
    }
}