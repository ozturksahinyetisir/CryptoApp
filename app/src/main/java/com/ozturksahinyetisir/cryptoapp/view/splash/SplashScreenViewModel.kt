package com.ozturksahinyetisir.cryptoapp.view.splash

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SplashScreenViewModel : ViewModel() {

    private val _arrowIconVisibility = MutableLiveData<Int>()
    val arrowIconVisibility: LiveData<Int> get() = _arrowIconVisibility

    init {
        _arrowIconVisibility.value = View.INVISIBLE
    }

    fun showArrowIcon() {
        _arrowIconVisibility.value = View.VISIBLE
    }

    fun hideArrowIcon() {
        _arrowIconVisibility.value = View.INVISIBLE
    }
}