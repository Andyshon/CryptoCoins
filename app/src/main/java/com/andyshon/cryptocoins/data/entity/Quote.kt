package com.andyshon.cryptocoins.data.entity

import com.google.gson.annotations.SerializedName

data class Quote(@SerializedName("USD") val usd: UsdCurrency)