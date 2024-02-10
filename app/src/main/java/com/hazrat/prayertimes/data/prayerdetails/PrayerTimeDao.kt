package com.hazrat.prayertimes.data.prayerdetails

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PrayerTimeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPrayerTimes(prayerTime: List<PrayerTimeEntity>)

    @Query("SELECT * FROM prayer_times")
    suspend fun getAllPrayer(): List<PrayerTimeEntity>

    @Query("SELECT * FROM prayer_times WHERE day = :day")
    suspend fun getPrayerTimeByDay(day: Int): PrayerTimeEntity?

    @Delete
    fun deletePrayerTime(prayerTime: List<PrayerTimeEntity>)

    @Query("DELETE FROM prayer_times")
    suspend fun deleteAllPrayer()


}