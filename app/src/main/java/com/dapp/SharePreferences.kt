package com.dapp

import android.content.Context
import com.panwallet.sdk.config.BlockChain

internal class SharePreferences(context: Context) {
    private val sharePreferences =
        context.getSharedPreferences(Constants.sharePreferencesName, Context.MODE_PRIVATE)

    fun saveChain(chain: BlockChain) {
        val editor = sharePreferences.edit()
        editor.putString(Constants.chain, chain.symbol)
        editor.apply()
    }

    fun getChain(): String? = sharePreferences.getString(Constants.chain, "")
}