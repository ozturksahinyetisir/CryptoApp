package com.ozturksahinyetisir.cryptoapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozturksahinyetisir.cryptoapp.data.model.CryptoInfo
import com.ozturksahinyetisir.cryptoapp.data.repository.CryptoRepository
import com.ozturksahinyetisir.cryptoapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

@HiltViewModel
class CryptoInfoViewModel @Inject constructor(private val cryptoRepository: CryptoRepository) : ViewModel() {

    private val _cryptoInfoList = MutableLiveData<Resource<List<CryptoInfo>>>()
    val allCryptos: LiveData<List<CryptoInfo>> = cryptoRepository.allCryptos

    private val _filteredCryptoInfoList = MutableLiveData<List<CryptoInfo>>()
    val filteredCryptoInfoList: LiveData<List<CryptoInfo>> = _filteredCryptoInfoList
    init {
        refreshCryptos()
    }
    fun refreshCryptos() {
        viewModelScope.launch {
            _cryptoInfoList.postValue(Resource.Loading())
            try {
                cryptoRepository.refreshCryptos()
                _cryptoInfoList.postValue(Resource.Success(cryptoRepository.allCryptos.value ?: emptyList()))
            } catch (e: Exception) {
                _cryptoInfoList.postValue(Resource.Error(e.message ?: "Unknown error"))
            }
        }
    }

    fun searchCryptoInfo(query: String) {
        viewModelScope.launch {
            _cryptoInfoList.postValue(Resource.Loading())
            try {
                val allCryptos = cryptoRepository.allCryptos.value ?: emptyList()
                val filteredList = allCryptos.filter { crypto ->
                   crypto.name.equals(query, ignoreCase = true) || crypto.symbol.equals(query, ignoreCase = true)
                }
                _filteredCryptoInfoList.postValue(filteredList)
            } catch (e: Exception) {
                _filteredCryptoInfoList.postValue(emptyList())
                Log.e("CryptoInfoViewModel", "Error searching cryptos: ${e.message}")
            }
        }
    }

    fun getAllCryptoNames(): List<String> {
        return allCryptos.value?.map { it.name } ?: emptyList()
    }
}

