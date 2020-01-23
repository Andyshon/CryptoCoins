package com.andyshon.cryptocoins.data.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Coin(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("symbol") val symbol: String,
    @SerializedName("slug") val slug: String,
    @SerializedName("num_market_pairs") val numMarketPairs: Int,
    @SerializedName("date_added") val dateAdded: String,
    @SerializedName("tags") val tags: List<String> = arrayListOf(),
    @SerializedName("max_supply") val maxSupply: Double,
    @SerializedName("circulating_supply") val circulatingSupply: Double,
    @SerializedName("total_supply") val totalSupply: Double,
    @SerializedName("cmc_rank") val cmcRank: Int,
    @SerializedName("last_updated") val lastUpdated: String,
    @SerializedName("quote") val quote: Quote
) : Parcelable