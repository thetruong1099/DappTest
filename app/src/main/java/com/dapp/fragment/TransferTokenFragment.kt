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
import com.dapp.databinding.FragmentTransferTokenBinding
import com.panwallet.sdk.config.BlockChain
import com.panwallet.sdk.connection.PanWalletManager


class TransferTokenFragment : Fragment() {
    private var _binding: FragmentTransferTokenBinding? = null
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
        _binding = FragmentTransferTokenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setGoBack()
        setSpinner()
        eventSendToken()
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

    private fun setSpinner() {
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

    private fun eventSendToken() {
        binding.btnSendToken.setOnClickListener {
            val contractAddress =
                if (binding.edtContractAddress.text.toString() == "") null else binding.edtContractAddress.text.toString()
            val addressReceiver = binding.edtAddressReceive.text.toString()
            val amount = binding.edtAmount.text.toString()

            if (addressReceiver != "" && amount != "") {
                try {
                    PanWalletManager.getInstance().requestTransferToken(
                        requireContext(),
                        network,
                        contractAddress,
                        amount.toFloat(),
                        addressReceiver,
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
            }
        }
    }
}