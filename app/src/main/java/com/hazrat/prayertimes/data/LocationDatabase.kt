package com.hazrat.prayertimes.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase



@Database(entities = [LocationEntity::class], version = 3, exportSchema = false)
abstract class LocationDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao

}