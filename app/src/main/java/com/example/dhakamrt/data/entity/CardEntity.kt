package com.example.dhakamrt.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * This represents ONE NFC card stored in database
 */
@Entity(tableName = "card_table")
data class CardEntity(

    // NFC card UID (unique)
    @PrimaryKey
    val cardUid: String,

    // Card balance (will update later)
    val balance: Int,

    // Last update time
    val lastUpdated: Long
)