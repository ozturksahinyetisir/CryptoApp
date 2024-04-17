package com.ozturksahinyetisir.cryptoapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ozturksahinyetisir.cryptoapp.data.model.CryptoInfo

@Database(entities = [CryptoInfo::class], version = 1,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cryptoDao(): CryptoDao
}