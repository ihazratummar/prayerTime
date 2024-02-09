package com.hazrat.prayertimes.data.location.coordinents

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [LocationEntity::class],version = 5, exportSchema = false)
abstract class LocationDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao


}