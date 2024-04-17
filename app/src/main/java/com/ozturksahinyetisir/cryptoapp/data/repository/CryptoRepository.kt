package com.ozturksahinyetisir.cryptoapp.data.repository

import com.ozturksahinyetisir.cryptoapp.data.model.CryptoInfo
import com.ozturksahinyetisir.cryptoapp.data.model.CryptoInfoResponse
import com.ozturksahinyetisir.cryptoapp.data.service.CryptoApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class CryptoRepository @Inject constructor(private val cryptoApi: CryptoApi) {

    fun getCryptoList(
        onSuccess: (cryptoList: List<CryptoInfo>) -> Unit,
        onFailure: (error: String) -> Unit
    ) {
        val call: Call<CryptoInfoResponse> = cryptoApi.getCryptoList()

        call.enqueue(object : Callback<CryptoInfoResponse> {
            override fun onResponse(call: Call<CryptoInfoResponse>, response: Response<CryptoInfoResponse>) {
                if (response.isSuccessful) {
                    val cryptoInfoResponse: CryptoInfoResponse? = response.body()
                    val cryptoList: List<CryptoInfo>? = cryptoInfoResponse?.data

                    cryptoList?.let { onSuccess(it) }
                } else {
                    onFailure("Response not successful: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<CryptoInfoResponse>, t: Throwable) {
                onFailure("API call failed: ${t.message}")
            }
        })
    }
}
