package com.ozturksahinyetisir.cryptoapp.di

import android.content.Context
import androidx.room.Room
import com.ozturksahinyetisir.cryptoapp.data.room.AppDatabase
import com.ozturksahinyetisir.cryptoapp.data.room.CryptoDao
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
}