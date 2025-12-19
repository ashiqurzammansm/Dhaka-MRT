package com.example.dhakamrt.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * MRT Station table
 */
@Entity(tableName = "station_table")
data class StationEntity(

    @PrimaryKey
    val stationId: Int,

    val nameEn: String,
    val nameBn: String,

    // Order in MRT Line-6
    val orderNo: Int
)