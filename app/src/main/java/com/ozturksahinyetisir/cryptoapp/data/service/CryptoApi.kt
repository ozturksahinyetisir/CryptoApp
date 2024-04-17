package com.ozturksahinyetisir.cryptoapp.data.service

import com.ozturksahinyetisir.cryptoapp.data.model.CryptoInfoResponse
import com.ozturksahinyetisir.cryptoapp.util.Constants
import retrofit2.Response
import retrofit2.http.GET

interface CryptoApi {
    @GET("cryptocurrency/listings/latest?CMC_PRO_API_KEY=${Constants.API_KEY}")
    suspend fun fetchCryptos(): Response<CryptoInfoResponse>
}