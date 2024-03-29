package com.hazrat.prayertimes.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLocation(location: LocationEntity)

    @Query("SELECT * FROM location_data WHERE id = 1")
    suspend fun getLocation(): LocationEntity?
}