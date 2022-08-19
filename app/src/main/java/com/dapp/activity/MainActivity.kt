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

        /*setChain()

        */

        /*connectDebug()
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
        disconnectMultiChain()
        disconnectBTC()
        disconnectETH()
        disconnectBSC()
        disconnectSOL()*/
    }

    override fun onResume() {
        super.onResume()

        /*  setTVChain()
        getDataOpening()
          */
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

    /* private fun setTVChain() {
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
                     1f,
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
                     1f,
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
             transaction["data"] = "asdasd"
             transaction["from"] = "asdasd"

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
             transaction["data"] = "asdasd"
             transaction["from"] = "asdasd"

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
         binding.btnSellNft.setOnClickListener {
             val transaction = mutableMapOf<String, String>()
             transaction["data"] = "asdad"
             transaction["from"] = "asdasd"

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
             transaction["data"] = "adasd"
             transaction["from"] = "asdasd"

             try {
                 PanWalletManager.getInstance().requestApproveDeposit(
                     this,
                     chain,
                     "0x0000",
                     1f,
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
             transaction["data"] = "asdasd"
             transaction["from"] = "asdasd"

             try {
                 PanWalletManager.getInstance().requestApproveUnlockBox(
                     this,
                     chain,
                     1f,
                     "0x0000",
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
             transaction["data"] = "0x000"
             transaction["from"] = "0x00"

             try {
                 PanWalletManager.getInstance().requestUnlockBox(
                     this,
                     chain,
                     "MOP",
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

     private fun openbox() {
         binding.btnOpenBox.setOnClickListener {
             val transaction = mutableMapOf<String, String>()
             transaction["data"] = "0x0000"
             transaction["from"] = "0x0000"

             try {
                 PanWalletManager.getInstance().requestOpenBox(
                     this,
                     chain,
                     "MOP",
                     "0x000000",
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


   */
}

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