package com.dapp.fragment

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
import androidx.navigation.fragment.findNavController
import com.dapp.R
import com.dapp.databinding.FragmentHomeBinding
import com.panwallet.sdk.connection.PanWalletManager


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var myReceiver: MyBroadcastReceiver? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateScreen()
        registerReceiver()
    }

    override fun onResume() {
        super.onResume()
        getDataOpening(requireContext(), requireActivity().intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        myReceiver?.let { it ->
            requireContext().unregisterReceiver(it)
        }
    }

    private fun navigateScreen() {
        binding.btnWalletConnect.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_walletConnectFragment)
        }

        binding.btnTransferToken.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_transferTokenFragment)
        }

        binding.btnDeposit.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_depositTokenFragment)
        }

        binding.btnTransferNft.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_transferNftFragment)
        }
    }

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