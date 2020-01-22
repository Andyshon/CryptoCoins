package com.andyshon.cryptocoins.data.repository

import com.andyshon.cryptocoins.data.entity.Coin
import com.andyshon.cryptocoins.data.model.CoinService
import io.reactivex.Single

class RepositoryImpl(private val api: CoinService) : Repository {

    override fun getCoins(): Single<List<Coin>> {
        return api.getCoins().map { it.data }
    }

}