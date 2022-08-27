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
import com.dapp.databinding.FragmentWalletConnectBinding
import com.panwallet.sdk.config.BlockChain
import com.panwallet.sdk.connection.PanWalletManager


class WalletConnectFragment : Fragment() {

    private var _binding: FragmentWalletConnectBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentWalletConnectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSpinner()
        setGoBack()
        eventConnect()
        eventDisconnect()
        eventSelectWalletConnected()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setGoBack() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_walletConnectFragment_to_homeFragment)
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
}