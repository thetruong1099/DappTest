package com.dapp.fragment

import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.dapp.R
import com.dapp.databinding.FragmentHomeBinding
import com.panwallet.sdk.config.BlockChain
import com.panwallet.sdk.config.NFT
import com.panwallet.sdk.connection.PanWalletManager


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var myReceiver: MyBroadcastReceiver? = null

    private val arrayChain = arrayListOf(
        BlockChain.MULTI_CHAIN.symbol,
        BlockChain.BITCOIN.symbol,
        BlockChain.ETHEREUM.symbol,
        BlockChain.BINANCE_SMART_CHAIN.symbol,
        BlockChain.SOLANA.symbol
    )
    private lateinit var chain: BlockChain

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        registerReceiver()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setSpinner()

        /*Connect*/
        eventConnect()
        eventSelectWalletConnected()
        eventDisconnect()

        /*Token*/
        eventSendToken()
        eventApproveDeposit()
        eventDeposit()
        eventWithDraw()

        /*NFT*/
        eventSendNft()
        eventApproveStakeNft()
        eventStakeNft()

        /*Box*/
        eventSendBox()

        eventCancelTransaction()

        getDataOpening(requireContext(), requireActivity().intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        myReceiver?.let { it ->
            requireContext().unregisterReceiver(it)
        }
    }

    private fun setSpinner() {
        val spinnerAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, arrayChain)

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
                            chain = BlockChain.MULTI_CHAIN
                        }
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }
        }
    }

    //request panwallet

    private fun eventConnect() {
        binding.btnConnect.setOnClickListener {
            try {
                PanWalletManager.getInstance().connectToWallet(chain, requireContext())
            } catch (e: Exception) {
                val alertDialogBuilder = AlertDialog.Builder(requireContext())

                alertDialogBuilder.apply {
                    setTitle("Alert dialog")
                    setMessage(e.message)
                    setPositiveButton("Cancel") { dialog, _ -> dialog.cancel() }
                    show()
                }
            }
        }
    }

    private fun eventSelectWalletConnected() {
        binding.btnSelectWalletConnected.setOnClickListener {
            try {
                PanWalletManager.getInstance()
                    .setCurrentWalletTypeConnected(requireContext(), chain)
            } catch (e: Exception) {
                val alertDialogBuilder = AlertDialog.Builder(requireContext())

                alertDialogBuilder.apply {
                    setTitle("Alert dialog")
                    setMessage(e.message)
                    setPositiveButton("Cancel") { dialog, _ -> dialog.cancel() }
                    show()
                }
            }
        }
    }

    private fun eventDisconnect() {
        binding.btnDisconnect.setOnClickListener {
            try {
                PanWalletManager.getInstance().disconnect(requireContext(), chain)
            } catch (e: Exception) {
                val alertDialogBuilder = AlertDialog.Builder(requireContext())

                alertDialogBuilder.apply {
                    setTitle("Alert dialog")
                    setMessage(e.message)
                    setPositiveButton("Cancel") { dialog, _ -> dialog.cancel() }
                    show()
                }
            }
        }
    }

    private fun eventSendToken() {
        binding.btnSendToken.setOnClickListener {
            try {
                PanWalletManager.getInstance().requestTransferToken(
                    requireContext(),
                    BlockChain.BINANCE_SMART_CHAIN,
                    "0x72491D3963b437ffc47563140a9BE8207Ff56e6F",
                    100f,
                    "0x29c0c2bEa26708282Aed3a87379A03cfc41624c4",
                )
            } catch (e: Exception) {
                val alertDialogBuilder = AlertDialog.Builder(requireContext())

                alertDialogBuilder.apply {
                    setTitle("Alert dialog")
                    setMessage(e.message)
                    setPositiveButton("Cancel") { dialog, _ -> dialog.cancel() }
                    show()
                }
            }
            /*val contractAddress =
                if (binding.edtContractAddress.text.toString() == "") null else binding.edtContractAddress.text.toString()
            val addressReceiver = binding.edtAddressReceive.text.toString()
            val amount = binding.edtAmount.text.toString()

            if (addressReceiver != "" && amount != "") {

            } else {
                val alertDialogBuilder = AlertDialog.Builder(requireContext())

                alertDialogBuilder.apply {
                    setTitle("Alert dialog")
                    setMessage("Không để trống address receive và amount")
                    setPositiveButton("Cancel") { dialog, _ -> dialog.cancel() }
                    show()
                }
            }*/
        }
    }

    private fun eventApproveDeposit() {
        //0xc7A0df7b30B977258164546F92E2c93367Cd0374
        //0x72491D3963b437ffc47563140a9BE8207Ff56e6F
        val transactionData = mapOf<String, Any>(
            "data" to "0x095ea7b300000000000000000000000049729aa559e42676b45f0d73740a0588c16d35df00000000000000000000000000000000000000000000000000470de4df820000",
            "from" to "0x3060275f556f582256b6f60e654471c5471d347b",
            "to" to "0x72491D3963b437ffc47563140a9BE8207Ff56e6F"
        )
        binding.btnApproveDepositToken.setOnClickListener {
            try {
                PanWalletManager.getInstance().requestApproveDepositToken(
                    requireContext(),
                    BlockChain.BINANCE_SMART_CHAIN,
                    "0x72491D3963b437ffc47563140a9BE8207Ff56e6F",
                    "0x49729Aa559e42676b45f0d73740a0588c16D35Df",//0x49729Aa559e42676b45f0d73740a0588c16D35Df
                    100.0f,
                    transactionData
                )
            } catch (e: Exception) {
                val alertDialogBuilder = AlertDialog.Builder(requireContext())

                alertDialogBuilder.apply {
                    setTitle("Alert dialog")
                    setMessage(e.message)
                    setPositiveButton("Cancel") { dialog, _ -> dialog.cancel() }
                    show()
                }
            }
        }
    }

    private fun eventDeposit() {
        val transactionData = mapOf<String, Any>(
            "data" to "0x095ea7b300000000000000000000000049729aa559e42676b45f0d73740a0588c16d35df00000000000000000000000000000000000000000000000000470de4df820000",
            "from" to "0x3060275f556f582256b6f60e654471c5471d347b",
            "to" to "0x72491D3963b437ffc47563140a9BE8207Ff56e6F"
        )
        binding.btnDepositToken.setOnClickListener {
            try {
                PanWalletManager.getInstance().requestDepositToken(
                    requireContext(),
                    BlockChain.BINANCE_SMART_CHAIN,
                    "0x72491D3963b437ffc47563140a9BE8207Ff56e6F",
                    0.1f,
                    "0x3060275f556f582256b6f60e654471c5471d347b",
                    transactionData
                )
            } catch (e: Exception) {
                val alertDialogBuilder = AlertDialog.Builder(requireContext())

                alertDialogBuilder.apply {
                    setTitle("Alert dialog")
                    setMessage(e.message)
                    setPositiveButton("Cancel") { dialog, _ -> dialog.cancel() }
                    show()
                }
            }
            /*val contractAddress = binding.edtContractAddress.text.toString()
            val amount = binding.edtAmount.text.toString()

            if (contractAddress != "" && amount != "") {
                try {
                    PanWalletManager.getInstance().requestDepositToken(
                        requireContext(),
                        BlockChain.BINANCE_SMART_CHAIN,
                        "0x72491D3963b437ffc47563140a9BE8207Ff56e6F",
                        1f,
                        "0x49729Aa559e42676b45f0d73740a0588c16D35Df"
                    )
                } catch (e: Exception) {
                    val alertDialogBuilder = AlertDialog.Builder(requireContext())

                    alertDialogBuilder.apply {
                        setTitle("Alert dialog")
                        setMessage(e.message)
                        setPositiveButton("Cancel") { dialog, _ -> dialog.cancel() }
                        show()
                    }
                }
            } else {
                val alertDialogBuilder = AlertDialog.Builder(requireContext())

                alertDialogBuilder.apply {
                    setTitle("Alert dialog")
                    setMessage("Không để trống address receive và amount")
                    setPositiveButton("Cancel") { dialog, _ -> dialog.cancel() }
                    show()
                }
            }*/
        }
    }

    private fun eventWithDraw() {
        val transactionData = mapOf<String, Any>(
            "data" to "0xeaead44a00000000000000000000000000000000000000000000000000000000000000800000000000000000000000000000000000000000000000000000000000000001000000000000000000000000000000000000000000000000000000000000000100000000000000000000000000000000000000000000000000000000000000c000000000000000000000000000000000000000000000000000000000000000076f72646572496400000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000004185931e84cfb611b1fed620b43fb002dfa1e6aaba4ce3727fa9af0b6455471de729eaa2dbc9f58f2865101915a4c5f63e7772877f479de18c60b7b4240771b8811b00000000000000000000000000000000000000000000000000000000000000",
            "from" to "0x3060275f556f582256b6f60e654471c5471d347b",
            "to" to "0xBf65081da652F8702ECA39374930d55Ac0A5f87B"
        )
        binding.btnWithdraw.setOnClickListener {
            try {
                PanWalletManager.getInstance().requestWithdrawToken(
                    requireContext(),
                    BlockChain.BINANCE_SMART_CHAIN,
                    "0x72491D3963b437ffc47563140a9BE8207Ff56e6F",
                    20f,
                    transactionData
                )
            } catch (e: Exception) {
                val alertDialogBuilder = AlertDialog.Builder(requireContext())

                alertDialogBuilder.apply {
                    setTitle("Alert dialog")
                    setMessage(e.message)
                    setPositiveButton("Cancel") { dialog, _ -> dialog.cancel() }
                    show()
                }
            }
        }
    }

    private fun eventApproveBuyNft() {
        val transactionData =
            mapOf<String, Any>(
                "to" to "0xAa437FB6Af74feBEfC2FFfa4FBBbe38605B752d7",
                "data" to "0x23b872dd00000000000000000000000029c0c2bea26708282aed3a87379a03cfc41624c4000000000000000000000000aa437fb6af74febefc2fffa4fbbbe38605b752d70000000000000000000000000000000000000000000000000000000000000000",
                "value" to "0x00"
            )
        binding.btnApproveBuyNft.setOnClickListener {
            try {
                PanWalletManager.getInstance().requestApproveBuyNft(
                    requireContext(),
                    BlockChain.BINANCE_SMART_CHAIN,
                    "0x4B977A6ADaA361CC49ba007335328Ab1Aa67fD5e",
                    "",
                    "MOP",
                    1f,
                    transactionData
                )
            } catch (e: Exception) {
                val alertDialogBuilder = AlertDialog.Builder(requireContext())

                alertDialogBuilder.apply {
                    setTitle("Alert dialog")
                    setMessage(e.message)
                    setPositiveButton("Cancel") { dialog, _ -> dialog.cancel() }
                    show()
                }
            }
        }
    }

    private fun eventBuyNft() {
        val transactionData =
            mapOf<String, Any>(
                "to" to "0xAa437FB6Af74feBEfC2FFfa4FBBbe38605B752d7",
                "data" to "0x23b872dd00000000000000000000000029c0c2bea26708282aed3a87379a03cfc41624c4000000000000000000000000aa437fb6af74febefc2fffa4fbbbe38605b752d70000000000000000000000000000000000000000000000000000000000000000",
                "value" to "0x00"
            )
        binding.btnBuyNft.setOnClickListener {
            try {
                PanWalletManager.getInstance().requestBuyNFT(
                    requireContext(),
                    BlockChain.BINANCE_SMART_CHAIN,
                    "0x12fCa23BfCA046F99cD417032A7DF54c4b3902b3",
                    "MOP",
                    NFT("0", null, "london1", null),
                    "0x2Aa030aBCa3299aB1891EE781F1fc780bE826681",
                    transactionData
                )
            } catch (e: Exception) {
                val alertDialogBuilder = AlertDialog.Builder(requireContext())

                alertDialogBuilder.apply {
                    setTitle("Alert dialog")
                    setMessage(e.message)
                    setPositiveButton("Cancel") { dialog, _ -> dialog.cancel() }
                    show()
                }
            }
        }
    }

    private fun eventApproveSellNft() {
        val transactionData =
            mapOf<String, Any>(
                "to" to "0xAa437FB6Af74feBEfC2FFfa4FBBbe38605B752d7",
                "data" to "0x23b872dd00000000000000000000000029c0c2bea26708282aed3a87379a03cfc41624c4000000000000000000000000aa437fb6af74febefc2fffa4fbbbe38605b752d70000000000000000000000000000000000000000000000000000000000000000",
                "value" to "0x00"
            )
        binding.btnApproveSellNft.setOnClickListener {
            try {
                PanWalletManager.getInstance().requestApproveSellNft(
                    requireContext(),
                    BlockChain.BINANCE_SMART_CHAIN,
                    "0x4B977A6ADaA361CC49ba007335328Ab1Aa67fD5e",
                    "",
                    transactionData
                )
            } catch (e: Exception) {
                val alertDialogBuilder = AlertDialog.Builder(requireContext())

                alertDialogBuilder.apply {
                    setTitle("Alert dialog")
                    setMessage(e.message)
                    setPositiveButton("Cancel") { dialog, _ -> dialog.cancel() }
                    show()
                }
            }
        }
    }

    private fun eventSellNft() {
        val transactionData =
            mapOf<String, Any>(
                "to" to "0xAa437FB6Af74feBEfC2FFfa4FBBbe38605B752d7",
                "data" to "0x23b872dd00000000000000000000000029c0c2bea26708282aed3a87379a03cfc41624c4000000000000000000000000aa437fb6af74febefc2fffa4fbbbe38605b752d70000000000000000000000000000000000000000000000000000000000000000",
                "value" to "0x00"
            )
        binding.btnSellNft.setOnClickListener {
            try {
                PanWalletManager.getInstance().requestSellNFT(
                    requireContext(),
                    BlockChain.BINANCE_SMART_CHAIN,
                    "0x12fCa23BfCA046F99cD417032A7DF54c4b3902b3",
                    "MOP",
                    NFT("0", null, "london1", null),
                    "0x2Aa030aBCa3299aB1891EE781F1fc780bE826681",
                    transactionData
                )
            } catch (e: Exception) {
                val alertDialogBuilder = AlertDialog.Builder(requireContext())

                alertDialogBuilder.apply {
                    setTitle("Alert dialog")
                    setMessage(e.message)
                    setPositiveButton("Cancel") { dialog, _ -> dialog.cancel() }
                    show()
                }
            }
        }
    }

    private fun eventSendNft() {
        val transactionData =
            mapOf<String, Any>(
                "to" to "0xAa437FB6Af74feBEfC2FFfa4FBBbe38605B752d7",
                "data" to "0x23b872dd00000000000000000000000029c0c2bea26708282aed3a87379a03cfc41624c4000000000000000000000000aa437fb6af74febefc2fffa4fbbbe38605b752d70000000000000000000000000000000000000000000000000000000000000000",
                "value" to "0x00"
            )
        binding.btnSendNft.setOnClickListener {
            try {
                PanWalletManager.getInstance().requestSendNFT(
                    requireContext(),
                    BlockChain.BINANCE_SMART_CHAIN,
                    "0x4B977A6ADaA361CC49ba007335328Ab1Aa67fD5e",
                    NFT("0", null, "london1", null),
                    "0xAa437FB6Af74feBEfC2FFfa4FBBbe38605B752d7",
                    transactionData
                )
            } catch (e: Exception) {
                val alertDialogBuilder = AlertDialog.Builder(requireContext())

                alertDialogBuilder.apply {
                    setTitle("Alert dialog")
                    setMessage(e.message)
                    setPositiveButton("Cancel") { dialog, _ -> dialog.cancel() }
                    show()
                }
            }
        }
    }

    private fun eventApproveStakeNft() {
        /*val transactionData =
            mapOf<String, Any>(
                "data" to "0x095ea7b300000000000000000000000030a1a7b6998e503fb393014346aa0712d3ed69e00000000000000000000000000000000000000000000000000000000000000041",
                "from" to "0x3060275f556f582256b6f60e654471c5471d347b",
                "to" to "0x3cef6E5B0cA46942E63324E07aF2f75aacf0fb66",
            )*/
        val transactionData = mapOf<String, Any>(
            "data" to "0x5bfadb2400000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000002",
            "to" to "0x2Aa030aBCa3299aB1891EE781F1fc780bE826681",
            "from" to "0x29c0c2bEa26708282Aed3a87379A03cfc41624c4"
        )
        binding.btnApproveStakeNft.setOnClickListener {
            try {
                PanWalletManager.getInstance().requestApproveStakeNft(
                    requireContext(),
                    BlockChain.BINANCE_SMART_CHAIN,
                    "0x12fCa23BfCA046F99cD417032A7DF54c4b3902b3",
                    "0x2Aa030aBCa3299aB1891EE781F1fc780bE826681",
                    transactionData
                )
            } catch (e: Exception) {
                val alertDialogBuilder = AlertDialog.Builder(requireContext())

                alertDialogBuilder.apply {
                    setTitle("Alert dialog")
                    setMessage(e.message)
                    setPositiveButton("Cancel") { dialog, _ -> dialog.cancel() }
                    show()
                }
            }
        }
    }

    private fun eventStakeNft() {
        val transactionData = mapOf<String, Any>(
            "data" to "0x5bfadb2400000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000002",
            "to" to "0x2Aa030aBCa3299aB1891EE781F1fc780bE826681",
            "from" to "0x29c0c2bEa26708282Aed3a87379A03cfc41624c4"
        )
        binding.btnStakeNft.setOnClickListener {
            try {
                PanWalletManager.getInstance().requestStakeNft(
                    requireContext(),
                    BlockChain.BINANCE_SMART_CHAIN,
                    "0x12fCa23BfCA046F99cD417032A7DF54c4b3902b3",
                    NFT("0", null, "london1", null),
                    "0x2Aa030aBCa3299aB1891EE781F1fc780bE826681",
                    transactionData
                )
            } catch (e: Exception) {
                val alertDialogBuilder = AlertDialog.Builder(requireContext())

                alertDialogBuilder.apply {
                    setTitle("Alert dialog")
                    setMessage(e.message)
                    setPositiveButton("Cancel") { dialog, _ -> dialog.cancel() }
                    show()
                }
            }
        }
    }

    private fun eventApproveBuyBox() {
        val transactionData = mapOf<String, Any>(
            "data" to "0x5bfadb2400000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000002",
            "to" to "0x2Aa030aBCa3299aB1891EE781F1fc780bE826681",
            "from" to "0x29c0c2bEa26708282Aed3a87379A03cfc41624c4"
        )
        binding.btnApproveBuyBox.setOnClickListener {
            try {
                PanWalletManager.getInstance().requestApproveBuyBox(
                    requireContext(),
                    BlockChain.BINANCE_SMART_CHAIN,
                    "0x4B977A6ADaA361CC49ba007335328Ab1Aa67fD5e",
                    "",
                    transactionData
                )
            } catch (e: Exception) {
                val alertDialogBuilder = AlertDialog.Builder(requireContext())

                alertDialogBuilder.apply {
                    setTitle("Alert dialog")
                    setMessage(e.message)
                    setPositiveButton("Cancel") { dialog, _ -> dialog.cancel() }
                    show()
                }
            }
        }
    }

    private fun eventBuyBox() {
        val transactionData = mapOf<String, Any>(
            "data" to "0x5bfadb2400000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000002",
            "to" to "0x2Aa030aBCa3299aB1891EE781F1fc780bE826681",
            "from" to "0x29c0c2bEa26708282Aed3a87379A03cfc41624c4"
        )
        binding.btnBuyBox.setOnClickListener {
            try {
                PanWalletManager.getInstance().requestBuyBox(
                    requireContext(),
                    BlockChain.BINANCE_SMART_CHAIN,
                    "Gen",
                    "0x12fCa23BfCA046F99cD417032A7DF54c4b3902b3",
                    "0x2Aa030aBCa3299aB1891EE781F1fc780bE826681",
                    transactionData
                )
            } catch (e: Exception) {
                val alertDialogBuilder = AlertDialog.Builder(requireContext())

                alertDialogBuilder.apply {
                    setTitle("Alert dialog")
                    setMessage(e.message)
                    setPositiveButton("Cancel") { dialog, _ -> dialog.cancel() }
                    show()
                }
            }
        }
    }

    private fun eventApproveUnlockBox() {
        val transactionData = mapOf<String, Any>(
            "data" to "0x5bfadb2400000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000002",
            "to" to "0x2Aa030aBCa3299aB1891EE781F1fc780bE826681",
            "from" to "0x29c0c2bEa26708282Aed3a87379A03cfc41624c4"
        )
        binding.btnApproveUnlockBox.setOnClickListener {
            try {
                PanWalletManager.getInstance().requestApproveUnlockBox(
                    requireContext(),
                    BlockChain.BINANCE_SMART_CHAIN,
                    "0x12fCa23BfCA046F99cD417032A7DF54c4b3902b3",
                    "",
                    transactionData
                )
            } catch (e: Exception) {
                val alertDialogBuilder = AlertDialog.Builder(requireContext())

                alertDialogBuilder.apply {
                    setTitle("Alert dialog")
                    setMessage(e.message)
                    setPositiveButton("Cancel") { dialog, _ -> dialog.cancel() }
                    show()
                }
            }
        }
    }

    private fun eventUnlockBox() {
        val transactionData = mapOf<String, Any>(
            "data" to "0x5bfadb2400000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001",
            "from" to "0x3060275f556f582256b6f60e654471c5471d347b",
            "to" to "0x2Aa030aBCa3299aB1891EE781F1fc780bE826681"
        )
        binding.btnUnlockBox.setOnClickListener {
            try {
                PanWalletManager.getInstance().requestUnlockBox(
                    requireContext(),
                    BlockChain.BINANCE_SMART_CHAIN,
                    "Gen",
                    "0x12fCa23BfCA046F99cD417032A7DF54c4b3902b3",
                    "0x2Aa030aBCa3299aB1891EE781F1fc780bE826681",
                    transactionData
                )
            } catch (e: Exception) {
                val alertDialogBuilder = AlertDialog.Builder(requireContext())

                alertDialogBuilder.apply {
                    setTitle("Alert dialog")
                    setMessage(e.message)
                    setPositiveButton("Cancel") { dialog, _ -> dialog.cancel() }
                    show()
                }
            }
        }
    }

    private fun eventOpenBox() {
        val transactionData = mapOf<String, Any>()
        binding.btnOpenBox.setOnClickListener {
            try {
                PanWalletManager.getInstance().requestOpenBox(
                    requireContext(),
                    BlockChain.BINANCE_SMART_CHAIN,
                    "Gen",
                    "0x4B977A6ADaA361CC49ba007335328Ab1Aa67fD5e",
                    transactionData
                )
            } catch (e: Exception) {
                val alertDialogBuilder = AlertDialog.Builder(requireContext())

                alertDialogBuilder.apply {
                    setTitle("Alert dialog")
                    setMessage(e.message)
                    setPositiveButton("Cancel") { dialog, _ -> dialog.cancel() }
                    show()
                }
            }
        }
    }

    private fun eventSendBox() {
        val transactionData = mapOf<String, Any>(
            "data" to "0xf242432a0000000000000000000000003060275f556f582256b6f60e654471c5471d347b00000000000000000000000029c0c2bea26708282aed3a87379a03cfc41624c40000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000100000000000000000000000000000000000000000000000000000000000000a0000000000000000000000000000000000000000000000000000000000000002a30783030303030303030303030303030303030303030303030303030303030303030303030303030303000000000000000000000000000000000000000000000",
            "from" to "0x3060275f556f582256b6f60e654471c5471d347b",
            "to" to "0x12fCa23BfCA046F99cD417032A7DF54c4b3902b3"
        )
        binding.btnSendBox.setOnClickListener {
            try {
                PanWalletManager.getInstance().requestSendBox(
                    requireContext(),
                    BlockChain.BINANCE_SMART_CHAIN,
                    "Gen",
                    "0x12fca23bfca046f99cd417032a7df54c4b3902b3",
                    "0x12fCa23BfCA046F99cD417032A7DF54c4b3902b3",
                    transactionData
                )
            } catch (e: Exception) {
                val alertDialogBuilder = AlertDialog.Builder(requireContext())

                alertDialogBuilder.apply {
                    setTitle("Alert dialog")
                    setMessage(e.message)
                    setPositiveButton("Cancel") { dialog, _ -> dialog.cancel() }
                    show()
                }
            }
        }
    }

    private fun eventCancelTransaction() {
        binding.btnCancelTransaction.setOnClickListener {
            try {
                PanWalletManager.getInstance().requestCancelTransaction(
                    requireContext()
                )
            } catch (e: Exception) {
                val alertDialogBuilder = AlertDialog.Builder(requireContext())

                alertDialogBuilder.apply {
                    setTitle("Alert dialog")
                    setMessage(e.message)
                    setPositiveButton("Cancel") { dialog, _ -> dialog.cancel() }
                    show()
                }
            }
        }
    }

    //get data from panwallet

    private fun getDataOpening(context: Context, intent: Intent) {
        try {
            val panConnection = PanWalletManager.getInstance()
            panConnection.getDataResponse(context, intent).let { data ->
                binding.tvCode.text = data.code.toString()
                binding.tvMessage.text = data.message
                binding.tvAddress.text = data.data.toString()
                Log.d("testApp", "getDataOpening: $data")
            }
        } catch (e: Exception) {
            Log.d("testDapp", "Exception: ${e.message}")
        }
    }

    private fun registerReceiver() {
        myReceiver = MyBroadcastReceiver()
        val intentFilter = IntentFilter(PanWalletManager.getInstance().getActionNameBroadcast())
        requireContext().registerReceiver(myReceiver, intentFilter)
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