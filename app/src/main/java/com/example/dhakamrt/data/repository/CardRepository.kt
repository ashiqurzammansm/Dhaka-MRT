package com.example.dhakamrt.data.repository

import com.example.dhakamrt.data.dao.CardDao
import com.example.dhakamrt.data.entity.CardEntity

/**
 * Repository
 * ViewModel talks to Repository
 * Repository talks to Room database
 */
class CardRepository(private val cardDao: CardDao) {

    // Save card
    suspend fun saveCard(card: CardEntity) {
        cardDao.insertCard(card)
    }

    // Read card by UID
    suspend fun getCardByUid(uid: String): CardEntity? {
        return cardDao.getCardByUid(uid)
    }
}
