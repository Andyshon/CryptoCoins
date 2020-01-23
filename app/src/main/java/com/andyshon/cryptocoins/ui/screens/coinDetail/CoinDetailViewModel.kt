package com.andyshon.cryptocoins.ui.screens.coinDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andyshon.cryptocoins.data.entity.Coin
import com.andyshon.cryptocoins.utils.extensions.default

sealed class CoinDetailState {
    object SetUI: CoinDetailState()
}

class CoinDetailViewModel : ViewModel() {

    lateinit var coin: Coin

    val state = MutableLiveData<CoinDetailState>().default(initialValue = CoinDetailState.SetUI)

}