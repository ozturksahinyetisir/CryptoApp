package com.ozturksahinyetisir.cryptoapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.ozturksahinyetisir.cryptoapp.data.model.CryptoInfo
import com.ozturksahinyetisir.cryptoapp.data.room.CryptoDao
import com.ozturksahinyetisir.cryptoapp.data.service.CryptoApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class CryptoRepository @Inject constructor(private val cryptoDao: CryptoDao, private val apiService: CryptoApi,) {

    val allCryptos: LiveData<List<CryptoInfo>> = cryptoDao.getAllCryptos()

    suspend fun refreshCryptos() {
        withContext(Dispatchers.IO) {
            try {
                val response = apiService.fetchCryptos()
                if (response.isSuccessful && response.body() != null) {
                    val cryptoList = response.body()!!.data
                    cryptoDao.insertAll(cryptoList)
                    Log.d("CryptoRepository", "Cryptos refreshed and inserted to DB.")
                } else {
                    throw HttpException(response)
                }
            } catch (e: Exception) {
                Log.e("CryptoRepository", "Error refreshing cryptos: ${e.message}")
                throw e
            }
        }
    }

    //TODO: Fix search query
    fun searchCryptos(query: String): LiveData<List<CryptoInfo>> {
        return cryptoDao.searchCryptos("%$query%")
    }

    fun getCryptoInfoByName(name: String): CryptoInfo? {
        return cryptoDao.getCryptoInfoByName(name)
    }

}
