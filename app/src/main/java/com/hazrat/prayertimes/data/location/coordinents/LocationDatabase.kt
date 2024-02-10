package com.hazrat.prayertimes.data.location.coordinents

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hazrat.prayertimes.data.location.locationdetails.LocationDetailsEntity
import com.hazrat.prayertimes.data.location.locationdetails.LocationNameDao


@Database(entities = [LocationEntity::class , LocationDetailsEntity::class],version = 6, exportSchema = false)
abstract class LocationDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao

    abstract fun locationNameDao(): LocationNameDao

}