package com.hazrat.prayertimes.data.location

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [LocationEntity::class], version = 3, exportSchema = false)
abstract class LocationDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao

}