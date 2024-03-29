package com.hazrat.prayertimes.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocationEntity::class], version = 1)
abstract class LocationDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao
}