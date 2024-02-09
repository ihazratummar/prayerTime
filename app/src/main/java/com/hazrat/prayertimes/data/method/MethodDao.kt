package com.hazrat.prayertimes.data.method

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MethodDao {

    //Method

    @Query("SELECT * FROM method_entity")
    fun getMethod(): Flow<List<MethodEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMethod(methodEntity: MethodEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateMethod(methodEntity: MethodEntity)

    @Query("DELETE FROM method_entity")
    suspend fun deleteAllMethod()

    @Delete
    suspend fun deleteMethod(methodEntity: MethodEntity)


}