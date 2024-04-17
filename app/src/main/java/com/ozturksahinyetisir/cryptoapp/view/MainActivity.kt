package com.ozturksahinyetisir.cryptoapp.view

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.ozturksahinyetisir.cryptoapp.R
import com.ozturksahinyetisir.cryptoapp.viewmodels.SharedViewModel
import com.ozturksahinyetisir.cryptoapp.data.service.RetrofitService
import com.ozturksahinyetisir.cryptoapp.databinding.ActivityMainBinding
import androidx.navigation.ui.NavigationUI
import com.ozturksahinyetisir.cryptoapp.data.repository.CryptoRepository
import com.ozturksahinyetisir.cryptoapp.view.home.HomeFragment
import com.ozturksahinyetisir.cryptoapp.view.splash.SplashFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var navController: NavController
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (sharedViewModel.shouldShowSplashScreen(this)) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_container, SplashFragment())
                .commit()

            sharedViewModel.markSplashScreenAsShown(this)
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_container, HomeFragment())
                .commit()
        }

        val cryptoRepository = CryptoRepository(RetrofitService.api)
        cryptoRepository.getCryptoList(
            onSuccess = { cryptoList ->
                cryptoList.forEach { cryptoInfo ->
                    println("Crypto Name: ${cryptoInfo.name}")
                    println("Symbol: ${cryptoInfo.symbol}")
                    println("CMC Rank: ${cryptoInfo.cmc_rank}")

                    val usdQuote = cryptoInfo.quote.USD
                    println("Price (USD): ${usdQuote.price}")
                    println("Volume 24h (USD): ${usdQuote.volume_24h}")
                    println("Max Supply: ${cryptoInfo.max_supply}")
                    println("Circulating Supply:${cryptoInfo.circulating_supply}")
                    println("-------------------------------")
                }
            },
            onFailure = { error ->
                Log.e("MAIN", error)
            }
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }
}

