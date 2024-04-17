package com.ozturksahinyetisir.cryptoapp.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    fun shouldShowSplashScreen(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        return !sharedPreferences.getBoolean("isSplashScreenShown", false)
    }

    fun markSplashScreenAsShown(context: Context) {
        val sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("isSplashScreenShown", true).apply()
    }


}