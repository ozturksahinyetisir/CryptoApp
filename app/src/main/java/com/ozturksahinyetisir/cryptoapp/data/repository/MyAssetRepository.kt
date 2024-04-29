package com.ozturksahinyetisir.cryptoapp.data.repository

import com.ozturksahinyetisir.cryptoapp.data.model.MyAsset
import com.ozturksahinyetisir.cryptoapp.data.room.MyAssetDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MyAssetRepository @Inject constructor(private val myAssetDao: MyAssetDao) {

    suspend fun insertMyAsset(asset: MyAsset) {
        withContext(Dispatchers.IO){
            myAssetDao.insertMyAsset(asset)
        }

    }

    suspend fun getAllAssets(): List<MyAsset> {
        return myAssetDao.getAllAssets()
    }

    suspend fun getTotalValue(): Double? {
        return myAssetDao.getTotalValue()
    }

}