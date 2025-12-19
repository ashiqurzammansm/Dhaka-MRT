package com.example.dhakamrt.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.dhakamrt.data.entity.StationEntity
import kotlin.math.abs

class FareViewModel(application: Application) : AndroidViewModel(application) {

    val stations = MutableLiveData<List<StationEntity>>()
    val fare = MutableLiveData<Int>()

    init {
        stations.value = listOf(
            StationEntity(1, "Uttara North", "উত্তরা উত্তর", 1),
            StationEntity(2, "Uttara Center", "উত্তরা সেন্টার", 2),
            StationEntity(3, "Pallabi", "পল্লবী", 3),
            StationEntity(4, "Mirpur 10", "মিরপুর ১০", 4),
            StationEntity(5, "Farmgate", "ফার্মগেট", 5),
            StationEntity(6, "Motijheel", "মতিঝিল", 6)
        )
    }

    fun calculateFare(start: Int, end: Int) {
        val stationGap = abs(start - end)
        fare.value = when {
            stationGap == 0 -> 0
            stationGap <= 2 -> 20
            stationGap <= 4 -> 30
            else -> 40
        }
    }
}
