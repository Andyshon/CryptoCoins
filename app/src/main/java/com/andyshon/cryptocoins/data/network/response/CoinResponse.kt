package com.andyshon.cryptocoins.data.network.response

import com.andyshon.cryptocoins.data.entity.Coin
import com.andyshon.cryptocoins.data.entity.Status
import com.google.gson.annotations.SerializedName

data class CoinResponse(
    @SerializedName("status") val status: Status,
    @SerializedName("data") val data: List<Coin>
)