package com.ozturksahinyetisir.cryptoapp.data.service

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ozturksahinyetisir.cryptoapp.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//TODO CryptoService set
object CryptoService {
    private val gson: Gson = GsonBuilder().create()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val api: CryptoApi by lazy {
        retrofit.create(CryptoApi::class.java)
    }
}