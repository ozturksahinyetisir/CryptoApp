package com.ozturksahinyetisir.cryptoapp.data.service

import com.ozturksahinyetisir.cryptoapp.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: CryptoApi by lazy {
        retrofit.create(CryptoApi::class.java)
    }
}