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
import com.dapp.databinding.FragmentDepositTokenBinding
import com.panwallet.sdk.config.BlockChain
import com.panwallet.sdk.connection.PanWalletManager

class DepositTokenFragment : Fragment() {
    private var _binding: FragmentDepositTokenBinding? = null
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
        _binding = FragmentDepositTokenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setGoBack()
        setSpinner()
        eventApproveDeposit()
        eventDeposit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setGoBack() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_depositTokenFragment_to_homeFragment)
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

    private fun eventApproveDeposit() {
        binding.btnApproveDepositToken.setOnClickListener {
            val contractAddress = binding.edtContractAddress.text.toString()
            val amount = binding.edtAmount.text.toString()

            if (contractAddress != "" && amount != "") {
                try {
                    PanWalletManager.getInstance().requestApproveDepositToken(
                        requireContext(),
                        network,
                        contractAddress,
                        amount.toFloat(),
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

    private fun eventDeposit() {
        binding.btnDepositToken.setOnClickListener {
            val contractAddress = binding.edtContractAddress.text.toString()
            val amount = binding.edtAmount.text.toString()

            if (contractAddress != "" && amount != "") {
                try {
                    PanWalletManager.getInstance().requestDepositToken(
                        requireContext(),
                        network,
                        contractAddress,
                        amount.toFloat(),
                        "0xAa437FB6Af74feBEfC2FFfa4FBBbe38605B752d7"
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