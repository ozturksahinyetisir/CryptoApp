package com.ozturksahinyetisir.cryptoapp.view.splash

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ozturksahinyetisir.cryptoapp.view.splash.pages.SplashScreenPageFragment

class SplashScreenPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SplashScreenPageFragment("Welcome to Crypto App")
            1 -> SplashScreenPageFragment("View Cryptocurrencies & Save your Portfolio")
            else -> SplashScreenPageFragment("Good earnings!")
        }
    }
}