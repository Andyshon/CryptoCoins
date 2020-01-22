package com.andyshon.cryptocoins.data.model

import com.andyshon.cryptocoins.BuildConfig
import com.andyshon.cryptocoins.data.network.response.CoinResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header

interface CoinService {
    @GET("/v1/cryptocurrency/listings/latest")
    fun getCoins(@Header("X-CMC_PRO_API_KEY") API_KEY: String = BuildConfig.API_KEY): Single<CoinResponse>

}