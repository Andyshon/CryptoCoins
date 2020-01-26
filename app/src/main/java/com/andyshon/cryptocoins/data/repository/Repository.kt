package com.andyshon.cryptocoins.data.repository

import com.andyshon.cryptocoins.data.entity.Coin
import io.reactivex.Completable
import io.reactivex.Observable

interface Repository {

    fun getCoins() : Observable<List<Coin>>
    fun getCoinsLocal(): Observable<List<Coin>>
    fun getCoinsApi(): Observable<List<Coin>>

    fun insertCoin(coin: Coin): Completable
    fun deleteAllCoins(): Completable
}