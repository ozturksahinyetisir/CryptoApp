package com.ozturksahinyetisir.cryptoapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_assets")
data class MyAsset(
    @PrimaryKey
    (autoGenerate = true)
    val id: Long = 0,
    val cryptoId: Int,
    val name: String,
    val symbol: String,
    val amount: Double,
    val price: Double,
    val totalValue: Double? = 0.0
)