package com.ozturksahinyetisir.cryptoapp.view

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI
import com.ozturksahinyetisir.cryptoapp.R
import com.ozturksahinyetisir.cryptoapp.data.model.CryptoInfo
import com.ozturksahinyetisir.cryptoapp.databinding.ActivityMainBinding
import com.ozturksahinyetisir.cryptoapp.util.Resource
import com.ozturksahinyetisir.cryptoapp.view.home.HomeFragment
import com.ozturksahinyetisir.cryptoapp.view.splash.SplashFragment
import com.ozturksahinyetisir.cryptoapp.viewmodels.CryptoInfoViewModel
import com.ozturksahinyetisir.cryptoapp.viewmodels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var navController: NavController
    private val sharedViewModel: SharedViewModel by viewModels()
    private val cryptoInfoViewModel: CryptoInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        observeCryptoData()


        // TODO : Fix splash screen & test.

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


    }

    //TODO : Remove println after all done.
    private fun observeCryptoData() {
        cryptoInfoViewModel.cryptoInfoList.observe(this) { resource ->
            when (resource) {
                is Resource.Success<*> -> (resource.data as? List<CryptoInfo>)?.let { cryptoList ->
                    for (crypto in cryptoList) {
                        println("Crypto Name: ${crypto.name}")
                        println("Symbol: ${crypto.symbol}")
                        println("CMC Rank: ${crypto.cmc_rank}")
                        val usdQuote = crypto.quote.USD
                        println("Price (USD): ${usdQuote.price}")
                        println("Volume 24h (USD): ${usdQuote.volume_24h}")
                        println("Max Supply: ${crypto.max_supply}")
                        println("Circulating Supply: ${crypto.circulating_supply}")
                        println("-------------------------------")
                    }
                }
                is Resource.Error<*> -> Log.e("MAIN", resource.message ?: "Unknown error")
                is Resource.Loading<*> -> Log.d("MAIN", "Data is loading...")
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }
}

