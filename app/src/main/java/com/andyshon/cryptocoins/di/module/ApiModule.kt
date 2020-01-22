package com.andyshon.cryptocoins.di.module

import com.andyshon.cryptocoins.BuildConfig
import com.andyshon.cryptocoins.data.model.CoinService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
object ApiModule {

    @Provides
    @JvmStatic
    @Singleton
    @Named("basic")
    fun provideApiClient(@Named("basicClient") client: OkHttpClient, @Named("basic") gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .build()
    }

    @Provides
    @JvmStatic
    @Singleton
    @Named("nulls")
    fun provideNullsApiClient(@Named("basicClient") client: OkHttpClient, @Named("nulls") gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .build()
    }

    @Provides
    @JvmStatic
    @Singleton
    fun provideCoinService(@Named("basic") retrofit: Retrofit): CoinService =
        retrofit.create(CoinService::class.java)

}
