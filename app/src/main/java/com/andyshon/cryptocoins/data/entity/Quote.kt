package com.andyshon.cryptocoins.data.entity

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Quote(@Embedded @SerializedName("USD") val usd: UsdCurrency) : Parcelable