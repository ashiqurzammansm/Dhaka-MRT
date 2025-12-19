package com.example.dhakamrt.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dhakamrt.data.entity.StationEntity

@Dao
interface StationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(stations: List<StationEntity>)

    @Query("SELECT * FROM station_table ORDER BY orderNo ASC")
    suspend fun getAllStations(): List<StationEntity>
}
