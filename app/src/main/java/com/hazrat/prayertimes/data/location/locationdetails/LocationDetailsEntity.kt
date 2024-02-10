package com.hazrat.prayertimes.data.location.locationdetails

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location_details")
data class LocationDetailsEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val village: String?,
    val city: String?
)
