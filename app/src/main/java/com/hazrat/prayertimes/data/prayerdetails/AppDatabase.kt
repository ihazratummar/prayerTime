package com.hazrat.prayertimes.data.prayerdetails

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PrayerTimeEntity::class], version = 8)
abstract class AppDatabase : RoomDatabase() {
    abstract fun prayerTimeDao(): PrayerTimeDao
}