package com.andyshon.cryptocoins.data.repository

import com.andyshon.cryptocoins.data.entity.Coin
import io.reactivex.Single

interface Repository {

    fun getCoins() : Single<List<Coin>>
}