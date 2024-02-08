package com.hazrat.prayertimes.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MethodEntity::class], version = 1)
abstract class MethodDatabase : RoomDatabase(){
    abstract fun methodDao(): MethodDao
}