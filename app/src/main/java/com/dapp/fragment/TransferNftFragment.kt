package com.dapp.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dapp.R
import com.dapp.databinding.FragmentTransferNftBinding
import com.panwallet.sdk.config.BlockChain
import com.panwallet.sdk.config.NFT
import com.panwallet.sdk.connection.PanWalletManager


class TransferNftFragment : Fragment() {
    private var _binding: FragmentTransferNftBinding? = null
    private val binding get() = _binding!!

    private val arrayNetwork = arrayListOf(
        BlockChain.BITCOIN.symbol,
        BlockChain.ETHEREUM.symbol,
        BlockChain.BINANCE_SMART_CHAIN.symbol,
        BlockChain.SOLANA.symbol
    )
    private lateinit var network: BlockChain

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTransferNftBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setGoBack()
        setSpinnerNetwork()
        setSpinnerNft()
        eventApproveBuyNft()
        eventBuyNft()
        eventApproveSellNft()
        eventSellNft()
        eventSendNft()
        eventApproveDepositNft()
        eventDepositNft()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setGoBack() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_transferNftFragment_to_homeFragment)
        }
    }

    private fun setSpinnerNetwork() {
        val spinnerNetworkAdapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                arrayNetwork
            )

        binding.spinnerNetwork.apply {
            adapter = spinnerNetworkAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    when (arrayNetwork[p2]) {
                        BlockChain.BITCOIN.symbol -> {
                            network = BlockChain.BITCOIN
                        }

                        BlockChain.ETHEREUM.symbol -> {
                            network = BlockChain.ETHEREUM
                        }

                        BlockChain.BINANCE_SMART_CHAIN.symbol -> {
                            network = BlockChain.BINANCE_SMART_CHAIN
                        }

                        BlockChain.SOLANA.symbol -> {
                            network = BlockChain.SOLANA
                        }

                        else -> {
                            network = BlockChain.BITCOIN
                        }
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }
        }
    }

    private fun setSpinnerNft() {
        val spinnerNetworkAdapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                arrayNetwork
            )

        binding.spinnerNft.apply {
            adapter = spinnerNetworkAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    when (arrayNetwork[p2]) {
                        BlockChain.BITCOIN.symbol -> {
                            network = BlockChain.BITCOIN
                        }

                        BlockChain.ETHEREUM.symbol -> {
                            network = BlockChain.ETHEREUM
                        }

                        BlockChain.BINANCE_SMART_CHAIN.symbol -> {
                            network = BlockChain.BINANCE_SMART_CHAIN
                        }

                        BlockChain.SOLANA.symbol -> {
                            network = BlockChain.SOLANA
                        }

                        else -> {
                            network = BlockChain.BITCOIN
                        }
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
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
                    network,
                    "0x4B977A6ADaA361CC49ba007335328Ab1Aa67fD5e",
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
                    network,
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
                    network,
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
                    network,
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
                    network,
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

    private fun eventApproveDepositNft() {
        val transactionData =
            mapOf<String, Any>(
                "to" to "0xAa437FB6Af74feBEfC2FFfa4FBBbe38605B752d7",
                "data" to "0x23b872dd00000000000000000000000029c0c2bea26708282aed3a87379a03cfc41624c4000000000000000000000000aa437fb6af74febefc2fffa4fbbbe38605b752d70000000000000000000000000000000000000000000000000000000000000000",
                "value" to "0x00"
            )
        binding.btnApproveDepositNft.setOnClickListener {
            try {
                PanWalletManager.getInstance().requestApproveStakeNft(
                    requireContext(),
                    network,
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

    private fun eventDepositNft() {
        val transactionData = mapOf<String, Any>(
            "data" to "0x5bfadb2400000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000002",
            "to" to "0x2Aa030aBCa3299aB1891EE781F1fc780bE826681",
            "from" to "0x29c0c2bEa26708282Aed3a87379A03cfc41624c4"
        )
        binding.btnDepositNft.setOnClickListener {
            try {
                PanWalletManager.getInstance().requestStakeNft(
                    requireContext(),
                    network,
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
}