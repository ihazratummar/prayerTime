package com.hazrat.prayertimes.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NonNls

@Entity(tableName = "method_entity")
data class MethodEntity(
    @PrimaryKey
    @ColumnInfo(name ="method")
    val method: Int
)
