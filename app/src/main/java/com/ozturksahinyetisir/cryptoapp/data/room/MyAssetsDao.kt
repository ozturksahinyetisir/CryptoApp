package com.ozturksahinyetisir.cryptoapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ozturksahinyetisir.cryptoapp.data.model.MyAsset

@Dao
interface MyAssetDao {
    @Query("SELECT * FROM my_assets")
    suspend fun getAllAssets(): List<MyAsset>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMyAsset(myAsset: MyAsset)

    @Query("SELECT SUM(totalValue) FROM my_assets")
    suspend fun getTotalValue(): Double
}