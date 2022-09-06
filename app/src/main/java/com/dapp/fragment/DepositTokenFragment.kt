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
        eventWithDraw()
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
        //0xc7A0df7b30B977258164546F92E2c93367Cd0374
        //0x72491D3963b437ffc47563140a9BE8207Ff56e6F
        val transactionData = mapOf<String, Any>(
            "data" to "0x5bfadb2400000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000002",
        )
        binding.btnApproveDepositToken.setOnClickListener {
            try {
                PanWalletManager.getInstance().requestApproveDepositToken(
                    requireContext(),
                    BlockChain.BINANCE_SMART_CHAIN,
                    "0x72491D3963b437ffc47563140a9BE8207Ff56e6F",
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

            /*val contractAddress = binding.edtContractAddress.text.toString()
            val amount = binding.edtAmount.text.toString()

            if (contractAddress != "" && amount != "") {
                try {
                    PanWalletManager.getInstance().requestApproveDepositToken(
                        requireContext(),
                        BlockChain.BINANCE_SMART_CHAIN,
                        " 0x72491D3963b437ffc47563140a9BE8207Ff56e6F",
                        100.0f,
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

    private fun eventDeposit() {
        binding.btnDepositToken.setOnClickListener {
            val contractAddress = binding.edtContractAddress.text.toString()
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
            }
        }
    }

    private fun eventWithDraw() {
        val transactionData = mapOf<String, Any>(
            "data" to "0x5bfadb2400000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000002",
        )
        binding.btnWithdraw.setOnClickListener {
            try {
                PanWalletManager.getInstance().requestWithdrawToken(
                    requireContext(),
                    BlockChain.BINANCE_SMART_CHAIN,
                    "0x72491D3963b437ffc47563140a9BE8207Ff56e6F",
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
}