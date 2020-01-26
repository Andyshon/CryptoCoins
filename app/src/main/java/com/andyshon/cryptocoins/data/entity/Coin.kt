package com.andyshon.cryptocoins.data.entity

import android.os.Parcelable
import androidx.room.*
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Coin(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("symbol") val symbol: String,
    @SerializedName("slug") val slug: String,
    @SerializedName("num_market_pairs") val numMarketPairs: Int,
    @SerializedName("date_added") val dateAdded: String,
    @Embedded @SerializedName("tags") var tags: ArrayList<String>? = null,
    @SerializedName("max_supply") val maxSupply: Double,
    @SerializedName("circulating_supply") val circulatingSupply: Double,
    @SerializedName("total_supply") val totalSupply: Double,
    @SerializedName("cmc_rank") val cmcRank: Int,
    @Embedded @SerializedName("quote") val quote: Quote
) : Parcelable