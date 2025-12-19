package com.example.dhakamrt.data.repository

import com.example.dhakamrt.data.dao.StationDao
import com.example.dhakamrt.data.entity.StationEntity

class FareRepository(private val stationDao: StationDao) {

    suspend fun getStations(): List<StationEntity> {
        return stationDao.getAllStations()
    }

    // Very simple fare logic (for learning)
    fun calculateFare(start: Int, end: Int): Int {
        val distance = kotlin.math.abs(start - end)
        return distance * 10   // 10 BDT per station gap
    }
}
