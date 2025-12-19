package com.example.dhakamrt.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dhakamrt.data.entity.CardEntity

/**
 * DAO = Database Access Object
 * This is how we talk to database
 */
@Dao
interface CardDao {

    // Save card (replace if already exists)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card: CardEntity)

    // Get saved card by UID
    @Query("SELECT * FROM card_table WHERE cardUid = :uid")
    suspend fun getCardByUid(uid: String): CardEntity?
}
