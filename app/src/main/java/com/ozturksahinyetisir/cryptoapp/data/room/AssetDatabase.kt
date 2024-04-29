package com.ozturksahinyetisir.cryptoapp.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ozturksahinyetisir.cryptoapp.data.model.MyAsset

@Database(entities = [MyAsset::class], version = 1, exportSchema = false)
abstract class AssetDatabase : RoomDatabase() {

    abstract fun myAssetDao(): MyAssetDao

    companion object {
        @Volatile
        private var INSTANCE: AssetDatabase? = null

        fun getDatabase(context: Context): AssetDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AssetDatabase::class.java,
                    "my_assets"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}