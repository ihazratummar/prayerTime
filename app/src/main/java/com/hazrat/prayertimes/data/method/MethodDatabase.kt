package com.hazrat.prayertimes.data.method

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hazrat.prayertimes.data.prayerdetails.PrayerTimeDao
import com.hazrat.prayertimes.data.prayerdetails.PrayerTimeEntity

@Database(entities = [MethodEntity::class], version = 10)
abstract class MethodDatabase : RoomDatabase() {
    abstract fun methodDao(): MethodDao
}