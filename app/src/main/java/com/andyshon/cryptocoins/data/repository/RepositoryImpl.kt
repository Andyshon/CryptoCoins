package com.andyshon.cryptocoins.data.repository

import android.content.Context
import com.andyshon.cryptocoins.data.entity.Coin
import com.andyshon.cryptocoins.data.model.CoinService
import com.andyshon.cryptocoins.data.repository.local.CoinsDAO
import com.andyshon.cryptocoins.di.qualifier.ApplicationContext
import com.andyshon.cryptocoins.utils.extensions.applySchedulers
import com.andyshon.cryptocoins.utils.extensions.isNetworkAvailable
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: CoinService,
    private val coinsDAO: CoinsDAO,
    @ApplicationContext private val context: Context) : Repository {

    override fun getCoins(): Observable<List<Coin>> {
        val fromApi by lazy { getCoinsApi() }
        val fromDb by lazy { getCoinsLocal() }

        if (context.isNetworkAvailable()) {
            deleteAllCoins().subscribe()
        }

        return if (context.isNetworkAvailable()) {
            Observable.concatArrayEager(fromApi, fromDb)
        } else fromDb
    }

    override fun getCoinsLocal(): Observable<List<Coin>> {
        return coinsDAO.getCoins().applySchedulers()
    }

    override fun getCoinsApi(): Observable<List<Coin>> {
        return api.getCoins()
            .toObservable()
            .map { it.data }
            .doOnNext { coins ->
                coins.forEach { coin ->
                    insertCoin(coin).subscribe()
                }
            }
    }

    override fun insertCoin(coin: Coin): Completable {
        return coinsDAO.insertCoin(coin).applySchedulers()
    }

    override fun deleteAllCoins(): Completable {
        return coinsDAO.deleteAll().applySchedulers()
    }

}