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
import com.dapp.databinding.FragmentTransferBoxBinding
import com.panwallet.sdk.config.BlockChain
import com.panwallet.sdk.config.ConnectType
import com.panwallet.sdk.connection.PanWalletManager

class TransferBoxFragment : Fragment() {
    private var _binding: FragmentTransferBoxBinding? = null
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
        _binding = FragmentTransferBoxBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setGoBack()
        eventApproveBuyBox()
        eventBuyBox()
        eventApproveUnlockBox()
        eventUnlockBox()
        eventOpenBox()
        eventSendBox()
        eventCancelTransaction()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setGoBack() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_transferBoxFragment_to_homeFragment)
        }
    }

    private fun eventApproveBuyBox() {
        /*val transactionData = mapOf<String, Any>(
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
        }*/
    }

    private fun eventBuyBox() {
       /* val transactionData = mapOf<String, Any>(
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
        }*/
    }

    private fun eventApproveUnlockBox() {
        /*val transactionData = mapOf<String, Any>(
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
        }*/
    }

    private fun eventUnlockBox() {
        /*val transactionData = mapOf<String, Any>(
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
        }*/
    }

    private fun eventOpenBox() {
        /*val transactionData = mapOf<String, Any>()
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
        }*/
    }

    private fun eventSendBox() {
        /*val transactionData = mapOf<String, Any>()
        binding.btnSendBox.setOnClickListener {
            try {
                PanWalletManager.getInstance().requestSendBox(
                    requireContext(),
                    BlockChain.BINANCE_SMART_CHAIN,
                    "Gen",
                    "0x4B977A6ADaA361CC49ba007335328Ab1Aa67fD5e",
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
        }*/
    }

    private fun eventCancelTransaction() {
        /*binding.btnCancelTransaction.setOnClickListener {
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
        }*/
    }
}