package com.ozturksahinyetisir.cryptoapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ozturksahinyetisir.cryptoapp.data.model.CryptoInfo
import com.ozturksahinyetisir.cryptoapp.data.repository.CryptoRepository
import com.ozturksahinyetisir.cryptoapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CryptoInfoViewModel @Inject constructor(private val cryptoRepository: CryptoRepository) : ViewModel() {

    private val _cryptoInfoList = MutableLiveData<Resource<List<CryptoInfo>>>()
    val cryptoInfoList: LiveData<Resource<List<CryptoInfo>>>
        get() = _cryptoInfoList

    fun getCryptoInfoList() {
        _cryptoInfoList.value = Resource.Loading()

        cryptoRepository.getCryptoList(
            onSuccess = { cryptoList ->
                _cryptoInfoList.value = Resource.Success(cryptoList)
            },
            onFailure = { error ->
                _cryptoInfoList.value = Resource.Error(error)
            }
        )
    }
}
