package com.dapp.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setGoBack() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_transferTokenFragment_to_homeFragment)
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
        binding.btnApproveBuyNft.setOnClickListener {

        }
    }

    private fun eventBuyNft() {
        val transactionData = mutableMapOf<String, Any>()
        binding.btnBuyNft.setOnClickListener {
            try {
                PanWalletManager.getInstance().requestBuyNFT(
                    requireContext(),
                    BlockChain.BINANCE_SMART_CHAIN,
                    "MOP",
                    "0x680d686E30eB2Fb93ec759c53Ef001a9AbaCdF7b",
                    NFT("152", null, "nft", null),
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
        binding.btnApproveSellNft.setOnClickListener {}
    }

    private fun eventSellNft() {
        binding.btnSellNft.setOnClickListener {
            val transactionData = mutableMapOf<String, Any>()
            try {
                PanWalletManager.getInstance().requestSellNFT(
                    requireContext(),
                    BlockChain.BINANCE_SMART_CHAIN,
                    "MOP",
                    "0x680d686E30eB2Fb93ec759c53Ef001a9AbaCdF7b",
                    NFT("152", null, "nft", null),
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
        val transactionData = mutableMapOf<String, Any>()
        transactionData["data"] =
            "0x23b872dd00000000000000000000000016f52d3d08a5af4ff72f4ae62f339aa444d780e1000000000000000000000000c3ad692f87ea0267c34f57c7752552338ae62daf0000000000000000000000000000000000000000000000000000000000000098"
        binding.btnSendNft.setOnClickListener {
            try {
                PanWalletManager.getInstance().requestSendNFT(
                    requireContext(),
                    BlockChain.BINANCE_SMART_CHAIN,
                    "0x680d686E30eB2Fb93ec759c53Ef001a9AbaCdF7b",
                    NFT("152", null, "nft", null),
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