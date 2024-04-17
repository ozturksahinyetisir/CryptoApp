package com.ozturksahinyetisir.cryptoapp.di

import com.ozturksahinyetisir.cryptoapp.data.repository.CryptoRepository
import com.ozturksahinyetisir.cryptoapp.data.service.CryptoApi
import com.ozturksahinyetisir.cryptoapp.util.Constants.BASE_URL
import com.ozturksahinyetisir.cryptoapp.viewmodels.CryptoInfoViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ActivityRetainedComponent::class)
object HiltModule {

    @ActivityRetainedScoped
    @Provides
    fun provideCryptoInfoViewModel(
        cryptoRepository: CryptoRepository
    ): CryptoInfoViewModel {
        return CryptoInfoViewModel(cryptoRepository)
    }

    @Provides
    fun provideCryptoApi(): CryptoApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoApi::class.java)
    }
}
