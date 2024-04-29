package com.ozturksahinyetisir.cryptoapp.di

import android.content.Context
import androidx.room.Room
import com.ozturksahinyetisir.cryptoapp.data.repository.MyAssetRepository
import com.ozturksahinyetisir.cryptoapp.data.room.AppDatabase
import com.ozturksahinyetisir.cryptoapp.data.room.AssetDatabase
import com.ozturksahinyetisir.cryptoapp.data.room.CryptoDao
import com.ozturksahinyetisir.cryptoapp.data.room.MyAssetDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "crypto_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideCryptoDao(database: AppDatabase): CryptoDao {
        return database.cryptoDao()
    }


    @Singleton
    @Provides
    fun provideAssetDatabase(@ApplicationContext context: Context): AssetDatabase {
        return Room.databaseBuilder(context, AssetDatabase::class.java, "asset_database")
            .fallbackToDestructiveMigration()
            .build()
    }
    @Provides
    fun provideMyAssetDao(assetDatabase: AssetDatabase): MyAssetDao {
        return assetDatabase.myAssetDao()
    }

    @Provides
    fun provideMyAssetRepository(myAssetDao: MyAssetDao): MyAssetRepository {
        return MyAssetRepository(myAssetDao)
    }
}