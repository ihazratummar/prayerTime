package com.hazrat.prayertimes.data.location.locationdetails

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LocationNameDao {
    @Query("SELECT * FROM location_details WHERE id = 1")
    suspend fun getLocationDetails(): LocationDetailsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLocationDetails(location: LocationDetailsEntity)

    @Query("DELETE FROM location_details WHERE id != 1")
    suspend fun deleteOtherLocationDetails()
}