package com.ozturksahinyetisir.cryptoapp.view.splash.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ozturksahinyetisir.cryptoapp.R

class SplashScreenPageFragment(private val pageText: String) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_splash_screen_page, container, false)
        val textView: TextView = view.findViewById(R.id.mainTv)
        textView.text = pageText

        return view
    }
}
