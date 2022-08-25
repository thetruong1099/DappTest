package com.dapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.dapp.BuildConfig
import com.dapp.R
import com.dapp.databinding.ActivityMainBinding
import com.panwallet.sdk.config.*
import com.panwallet.sdk.connection.PanWalletManager


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        setConfig()
    }

    private fun setConfig() {
        val config = ConfigWalletService(
            "Dapp Test",
            "app://com.dapp",
            "https://ddsolution.co/",
            null,
            BuildConfig.APPLICATION_ID
        )

        PanWalletManager.getInstance().setConfig(config, this)
    }
}