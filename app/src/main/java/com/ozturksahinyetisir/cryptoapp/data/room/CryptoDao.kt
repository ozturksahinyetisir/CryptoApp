package com.ozturksahinyetisir.cryptoapp.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ozturksahinyetisir.cryptoapp.data.model.CryptoInfo

@Dao
interface CryptoDao{
    @Query("SELECT * FROM crypto_info WHERE name LIKE :query OR symbol LIKE :query")
    fun searchCryptos(query: String): LiveData<List<CryptoInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cryptos: List<com.ozturksahinyetisir.cryptoapp.data.model.CryptoInfo>)

    @Query("SELECT * FROM crypto_info")
    fun getAllCryptos(): LiveData<List<CryptoInfo>>

    @Query("SELECT COUNT(*) FROM crypto_info")
    fun getCount(): Int
}