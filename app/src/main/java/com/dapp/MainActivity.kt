package com.dapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.dapp.databinding.ActivityMainBinding
import com.panwallet.sdk.config.*
import com.panwallet.sdk.connection.PanWalletManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val arrayChain = arrayListOf(
        BlockChain.BITCOIN.symbol,
        BlockChain.ETHEREUM.symbol,
        BlockChain.BINANCE_SMART_CHAIN.symbol,
        BlockChain.SOLANA.symbol
    )

    private lateinit var chain: BlockChain

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSpinner()

        connectDebug()

        connectRelease()

        Log.d("aaaa", "onCreate: ")
    }

    private fun setSpinner() {
        val spinnerAdapter =
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, arrayChain)

        binding.spinnerChain.apply {
            adapter = spinnerAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    when (arrayChain[p2]) {
                        BlockChain.BITCOIN.symbol -> {
                            chain = BlockChain.BITCOIN
                        }

                        BlockChain.ETHEREUM.symbol -> {
                            chain = BlockChain.ETHEREUM
                        }

                        BlockChain.BINANCE_SMART_CHAIN.symbol -> {
                            chain = BlockChain.BINANCE_SMART_CHAIN
                        }

                        BlockChain.SOLANA.symbol -> {
                            chain = BlockChain.SOLANA
                        }

                        else -> {
                            chain = BlockChain.BITCOIN
                        }
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }
        }
    }

    private fun connectDebug() {
        binding.btnConnectModeDebug.setOnClickListener {
            try {
                val config = ConfigWalletService(
                    "Dapp Test",
                    "app://com.dapp",
                    "https://ddsolution.co/",
                    null,
                    BuildMode.DEBUG,
                    null,
                    DebugConfig(Ethereum.ROPSTEN, Solana.TEST_NET),
                    BuildConfig.APPLICATION_ID
                )

                Log.d("testDapp", "connectDebug: ${config.applicationID}")

                val panConnection = PanWalletManager()

                panConnection.setConfig(config, this)

                Log.d("testDapp", "connect: $chain")

                panConnection.connectToWallet(chain, this)

            } catch (e: Exception) {
                val alertDialogBuilder = AlertDialog.Builder(this)

                alertDialogBuilder.apply {
                    setTitle("Alert dialog")
                    setMessage(e.message)
                    setPositiveButton("Cancel") { dialog, _ -> dialog.cancel() }
                    show()
                }
            }
        }
    }

    private fun connectRelease() {
        binding.btnConnectModeRelease.setOnClickListener {
            try {
                val config = ConfigWalletService(
                    "Dapp Test",
                    "app://com.dapp",
                    "https://ddsolution.co/",
                    null,
                    BuildMode.RELEASE,
                    null,
                    DebugConfig(Ethereum.ROPSTEN, Solana.TEST_NET),
                    BuildConfig.APPLICATION_ID
                )

                Log.d("testDapp", "connectDebug: ${config.applicationID}")

                val panConnection = PanWalletManager()

                panConnection.setConfig(config, this)

                Log.d("testDapp", "connect: $chain")

                panConnection.connectToWallet(chain, this)

            } catch (e: Exception) {
                val alertDialogBuilder = AlertDialog.Builder(this)

                alertDialogBuilder.apply {
                    setTitle("Alert dialog")
                    setMessage(e.message)
                    setPositiveButton("Cancel") { dialog, _ -> dialog.cancel() }
                    show()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getDataOpening()
        Log.d("aaaa", "onResume: ")
    }

    private fun getDataOpening() {
        val data = intent.data
        Log.d("testDapp", "getDataOpening uri: $data")
        try {
            val panConnection = PanWalletManager()
            panConnection.getDataResponse(this@MainActivity)?.let { data ->
                binding.tvCode.text = data.code.toString()
                binding.tvMessage.text = data.message
                binding.tvAddress.text = data.data["address"]
            }
        } catch (e: Exception) {
            Log.d("testDapp", "Exception: ${e.message}")
        }
    }

}