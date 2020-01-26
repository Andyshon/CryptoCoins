package com.andyshon.cryptocoins.data.entity

import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class UsdCurrency(
    @SerializedName("price") val price: Double,
    @SerializedName("volume_24h") val volume24h: Double,
    @SerializedName("percent_change_1h") val percentChange1h: Double,
    @SerializedName("percent_change_24h") val percentChange24h: Double,
    @SerializedName("percent_change_7d") val percentChange7d: Double,
    @SerializedName("market_cap") val marketCap: Double
) : Parcelable