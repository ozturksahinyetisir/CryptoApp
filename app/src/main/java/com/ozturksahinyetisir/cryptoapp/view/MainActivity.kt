package com.ozturksahinyetisir.cryptoapp.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI
import com.ozturksahinyetisir.cryptoapp.R
import com.ozturksahinyetisir.cryptoapp.databinding.ActivityMainBinding
import com.ozturksahinyetisir.cryptoapp.view.home.HomeFragment
import com.ozturksahinyetisir.cryptoapp.view.splash.SplashFragment
import com.ozturksahinyetisir.cryptoapp.viewmodels.SharedViewModel
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
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }
}

