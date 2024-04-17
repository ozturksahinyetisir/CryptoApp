package com.ozturksahinyetisir.cryptoapp.viewmodels

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

    val cryptoInfoList: LiveData<List<CryptoInfo>> = cryptoRepository.allCryptos

    private val _cryptoInfoList = MutableLiveData<Resource<List<CryptoInfo>>>()
    val cryptoInfoResource: LiveData<Resource<List<CryptoInfo>>> = _cryptoInfoList

    init {
        refreshCryptos()
    }
    private fun refreshCryptos() {
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

    // TODO: Search Query

}

