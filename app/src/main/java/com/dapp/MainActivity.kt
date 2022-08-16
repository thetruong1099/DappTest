package com.dapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.opengl.ETC1.isValid
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dapp.databinding.ActivityMainBinding
import com.panwallet.sdk.config.*
import com.panwallet.sdk.connection.PanWalletManager
import org.json.JSONException
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var myReceiver: MyBroadcastReceiver? = null

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

        setChain()

        setConfig()

        registerReceiver()

        connectDebug()
        sendToken()
        approveBuyNft()
        approveSellNft()
        sendNft()
        buyNft()
        sellNft()
        approveDeposit()
        deposit()
        approveUnlockBox()
        unlockBox()
        openbox()
        disconnect()
    }

    override fun onResume() {
        super.onResume()
        setTVChain()
        getDataOpening()
    }

    override fun onDestroy() {
        super.onDestroy()
        myReceiver?.let { it ->
            unregisterReceiver(it)
        }
    }

    private fun setTVChain() {
        val chainConnect = SharePreferences(this).getChain()
        binding.tvChainConnect.text = chainConnect
        when (chainConnect) {
            BlockChain.MULTI_CHAIN.symbol -> chain = BlockChain.MULTI_CHAIN

            BlockChain.BITCOIN.symbol -> chain = BlockChain.BITCOIN

            BlockChain.ETHEREUM.symbol -> chain = BlockChain.ETHEREUM

            BlockChain.BINANCE_SMART_CHAIN.symbol -> chain = BlockChain.BINANCE_SMART_CHAIN

            BlockChain.SOLANA.symbol -> chain = BlockChain.SOLANA
        }
    }

    private fun setChain() {
        binding.btnMultiChain.setOnClickListener {
            chain = BlockChain.MULTI_CHAIN
            binding.tvChainConnect.text = chain.symbol
        }

        binding.btnBtc.setOnClickListener {
            chain = BlockChain.BITCOIN
            binding.tvChainConnect.text = chain.symbol
        }

        binding.btnEth.setOnClickListener {
            chain = BlockChain.ETHEREUM
            binding.tvChainConnect.text = chain.symbol
        }

        binding.btnBsc.setOnClickListener {
            chain = BlockChain.BINANCE_SMART_CHAIN
            binding.tvChainConnect.text = chain.symbol
        }

        binding.btnSol.setOnClickListener {
            chain = BlockChain.SOLANA
            binding.tvChainConnect.text = chain.symbol
        }
    }

    private fun setConfig() {
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

        PanWalletManager.getInstance().setConfig(config, this)
    }

    private fun connectDebug() {
        binding.btnConnectModeDebug.setOnClickListener {
            try {
                PanWalletManager.getInstance().connectToWallet(chain, this)
                SharePreferences(this).saveChain(chain)
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

    private fun sendToken() {
        binding.btnSendToken.setOnClickListener {
            try {
                PanWalletManager.getInstance().requestTransferToken(
                    this,
                    chain,
                    null,
                    0.5f,
                    "0xAa437FB6Af74feBEfC2FFfa4FBBbe38605B752d7",
                )
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

    private fun approveBuyNft() {
        val transaction = mutableMapOf<String, Any>()
        transaction["data"] = "0x1111"

        binding.btnApprovedBuyNft.setOnClickListener {
            try {
                PanWalletManager.getInstance().requestApproveBuyNft(
                    this,
                    chain,
                    "0x00",
                    "MOP",
                    transaction
                )
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

    private fun approveSellNft() {
        val transaction = mutableMapOf<String, Any>()
        transaction["data"] = "0x1111"

        binding.btnApprovedSellNft.setOnClickListener {
            try {
                PanWalletManager.getInstance().requestApproveSellNft(
                    this,
                    chain,
                    "0x00",
                    transaction
                )
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

    private fun sendNft() {
        binding.btnSendNft.setOnClickListener {
            val transaction = mutableMapOf<String, String>()
            transaction["data"] = ""
            transaction["from"] = ""

            try {
                PanWalletManager.getInstance().requestSendNFT(
                    this, chain, "ETH", "0x001313464",
                    NFT("1", "https://", "asd", 0.5f),
                    transaction
                )
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

    private fun buyNft() {
        binding.btnBuyNft.setOnClickListener {
            val transaction = mutableMapOf<String, String>()
            transaction["data"] = ""
            transaction["from"] = ""

            try {
                PanWalletManager.getInstance().requestBuyNFT(
                    this, chain, "ETH", "0x001313464",
                    NFT("1", "https://", "asd", 0.5f),
                    transaction
                )
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

    private fun sellNft() {
        binding.btnBuyNft.setOnClickListener {
            val transaction = mutableMapOf<String, String>()
            transaction["data"] = ""
            transaction["from"] = ""

            try {
                PanWalletManager.getInstance().requestSellNFT(
                    this, chain, "ETH", "0x001313464",
                    NFT("1", "https://", "asd", 0.5f),
                    transaction
                )
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

    private fun approveDeposit() {
        binding.btnApprovedDeposit.setOnClickListener {
            val transaction = mutableMapOf<String, String>()
            transaction["data"] = ""
            transaction["from"] = ""

            try {
                PanWalletManager.getInstance().requestApproveDeposit(
                    this,
                    chain,
                    "",
                    0f,
                    transaction
                )
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

    private fun deposit() {
        binding.btnDeposit.setOnClickListener {
            val transaction = mutableMapOf<String, String>()
            transaction["data"] = ""
            transaction["from"] = ""

            try {
                PanWalletManager.getInstance().requestDeposit(
                    this,
                    chain,
                    0f,
                    "",
                )
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

    private fun approveUnlockBox() {
        binding.btnApprovedUnlockBox.setOnClickListener {
            val transaction = mutableMapOf<String, String>()
            transaction["data"] = ""
            transaction["from"] = ""

            try {
                PanWalletManager.getInstance().requestApproveUnlockBox(
                    this,
                    chain,
                    "",
                    transaction
                )
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

    private fun unlockBox() {
        binding.btnUnlockBox.setOnClickListener {
            val transaction = mutableMapOf<String, String>()
            transaction["data"] = ""
            transaction["from"] = ""

            try {
                PanWalletManager.getInstance().requestUnlockBox(
                    this,
                    chain,
                    "",
                    "",
                    transaction
                )
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

    private fun openbox() {
        binding.btnOpenBox.setOnClickListener {
            val transaction = mutableMapOf<String, String>()
            transaction["data"] = ""
            transaction["from"] = ""

            try {
                PanWalletManager.getInstance().requestOpenBox(
                    this,
                    chain,
                    "",
                    "",
                    transaction
                )
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

    private fun disconnect() {
        binding.btnDisconnect.setOnClickListener {
            try {
                PanWalletManager.getInstance().disconnect(this)
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

    private fun getDataOpening() {
        try {
            val panConnection = PanWalletManager.getInstance()
            panConnection.getDataResponse(this, intent).let { data ->
                binding.tvCode.text = data.code.toString()
                binding.tvMessage.text = data.message
                binding.tvAddress.text = data.data.toString()
            }
        } catch (e: Exception) {
            Log.d("testDapp", "Exception: ${e.message}")
        }
    }

    private fun registerReceiver() {
        myReceiver = MyBroadcastReceiver()
        val intentFilter = IntentFilter(PanWalletManager.getInstance().getActionNameBroadcast())
        registerReceiver(myReceiver, intentFilter)
    }

    private class MyBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            if (p0 != null && p1 != null) {
                val response = PanWalletManager.getInstance().getDataResponse(p0, p1)
                Log.d("testApp", "onReceive: $response")
            }

        }
    }
}

/*    private fun setSpinner() {
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
    }*/

/*private fun testConnect() {
    binding.btnSendToken.setOnClickListener {
        //Declare data send
        val i = Intent()
        i.putExtra("data", "Duong Vu Tru")
        i.action = "com.panwallet.data"

        //Declare open app
        var intent: Intent? =
            this.applicationContext.packageManager.getLaunchIntentForPackage(packageName)
        if (intent != null) {
            intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("app://com.moveplus.app.debug")
            intent.putExtra("data", "Duong Vu Tru")
        }
        intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        //open app
        this.applicationContext.startActivity(intent)
        //send data
        applicationContext.sendBroadcast(i)
    }
}*/