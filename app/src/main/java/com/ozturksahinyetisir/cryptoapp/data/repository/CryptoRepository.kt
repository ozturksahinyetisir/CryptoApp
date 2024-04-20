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

    suspend fun refreshCryptos(forceUpdate: Boolean = false) {
        withContext(Dispatchers.IO) {
            // TODO : forceUpdate set or remove.
            val shouldFetch = forceUpdate || cryptoDao.getCount() == 0
            if (shouldFetch) {
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
    }

    //TODO: Fix search query
    fun searchCryptos(query: String): LiveData<List<CryptoInfo>> {
        return cryptoDao.searchCryptos("%$query%")
    }
}
