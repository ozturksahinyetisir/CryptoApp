package com.ozturksahinyetisir.cryptoapp.data.repository

import androidx.lifecycle.LiveData
import com.ozturksahinyetisir.cryptoapp.data.model.CryptoInfo
import com.ozturksahinyetisir.cryptoapp.data.room.CryptoDao
import com.ozturksahinyetisir.cryptoapp.data.service.CryptoApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class CryptoRepository @Inject constructor(private val cryptoDao: CryptoDao, private val apiService: CryptoApi) {

    val allCryptos: LiveData<List<CryptoInfo>> = cryptoDao.getAllCryptos()

    suspend fun refreshCryptos() {
        withContext(Dispatchers.IO) {
            try {
                val response = apiService.fetchCryptos()
                if (response.isSuccessful && response.body() != null) {
                    cryptoDao.insertAll(response.body()!!.data)
                } else {
                    throw HttpException(response)
                }
            } catch (e: Exception) {

                throw e
            }
        }
    }

    // TODO : Check if the local database is empty
    suspend fun getInitialCryptos(): LiveData<List<CryptoInfo>> {
        withContext(Dispatchers.IO) {
            if (cryptoDao.getCount() == 0) {
                try {
                    val response = apiService.fetchCryptos()
                    if (response.isSuccessful && response.body() != null) {
                        cryptoDao.insertAll(response.body()!!.data)
                    } else {
                        throw HttpException(response)
                    }
                } catch (e: Exception) {
                    // TODO: Log.e
                }
            }
        }
        return allCryptos
    }

    //TODO: Fix search query
    fun searchCryptos(query: String): LiveData<List<CryptoInfo>> {
        return cryptoDao.searchCryptos("%$query%")
    }
}
