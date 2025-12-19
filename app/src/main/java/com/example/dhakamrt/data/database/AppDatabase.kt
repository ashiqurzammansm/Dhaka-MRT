package com.example.dhakamrt.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dhakamrt.data.dao.CardDao
import com.example.dhakamrt.data.entity.CardEntity

/**
 * Main database of the app
 */
@Database(
    entities = [CardEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cardDao(): CardDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "dhaka_mrt_db"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}
