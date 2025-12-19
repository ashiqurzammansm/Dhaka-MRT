package com.example.dhakamrt.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dhakamrt.data.database.AppDatabase
import com.example.dhakamrt.data.entity.CardEntity
import com.example.dhakamrt.data.repository.CardRepository
import kotlinx.coroutines.launch

/**
 * ViewModel
 * Holds business logic (balance, warning, database)
 */
class CardViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CardRepository

    private val _cardData = MutableLiveData<CardEntity?>()
    val cardData: LiveData<CardEntity?> = _cardData

    init {
        val dao = AppDatabase.getDatabase(application).cardDao()
        repository = CardRepository(dao)
    }

    /**
     * Save card with balance
     * (for now balance is mocked)
     */
    fun saveCard(uid: String) {
        viewModelScope.launch {

            // TEMP balance (later will come from FeliCa)
            val balanceFromCard = 15   // 🔴 LOW BALANCE TEST VALUE

            val card = CardEntity(
                cardUid = uid,
                balance = balanceFromCard,
                lastUpdated = System.currentTimeMillis()
            )
            repository.saveCard(card)
        }
    }

    /**
     * Read card from Room
     */
    fun readCard(uid: String) {
        viewModelScope.launch {
            _cardData.postValue(repository.getCardByUid(uid))
        }
    }

    /**
     * Check if balance is low (≤ 18 BDT)
     */
    fun isLowBalance(balance: Int): Boolean {
        return balance <= 18
    }
}
