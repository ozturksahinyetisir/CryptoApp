package com.ozturksahinyetisir.cryptoapp.util

import android.graphics.Color
import android.os.CountDownTimer
import android.util.Log
import android.widget.ImageButton
import com.ozturksahinyetisir.cryptoapp.R

class RefreshCountDownTimer(private val refreshButton: ImageButton) : CountDownTimer(30000, 1000) {

    override fun onTick(millisUntilFinished: Long) {
        val secondsRemaining = millisUntilFinished / 1000
        Log.d("RefreshCountDownTimer", "Seconds Remaining: $secondsRemaining")
        refreshButton.isEnabled = false // Butonun tıklanmasını engelle
        refreshButton.setImageResource(R.drawable.ic_refresh)
        refreshButton.setColorFilter(Color.RED)// Buton rengini değiştir
    }

    override fun onFinish() {
        Log.d("RefreshCountDownTimer", "Timer Finished")
        refreshButton.isEnabled = true
        refreshButton.setImageResource(R.drawable.ic_refresh)
        refreshButton.setColorFilter(Color.WHITE)
    }
}