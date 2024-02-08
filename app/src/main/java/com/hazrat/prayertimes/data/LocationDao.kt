package com.hazrat.prayertimes.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface LocationDao {
    @Query("SELECT * FROM location_data WHERE id = 1")
    suspend fun getLocation(): LocationEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLocation(location: LocationEntity)


}