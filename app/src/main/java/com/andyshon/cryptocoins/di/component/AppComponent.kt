package com.andyshon.cryptocoins.di.component

import com.andyshon.cryptocoins.di.module.*
import com.andyshon.cryptocoins.ui.screens.coinDetail.CoinDetailActivity
import com.andyshon.cryptocoins.ui.screens.main.MainActivity
import com.andyshon.cryptocoins.ui.screens.coinsList.CoinsListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApiModule::class,
        ViewModelModule::class,
        RepositoryModule::class,
        AppModule::class,
        NetworkModule::class
    ]
)
interface AppComponent {

    fun inject(activity: MainActivity)
    fun inject(activity: CoinDetailActivity)
    fun inject(fragment: CoinsListFragment)
}