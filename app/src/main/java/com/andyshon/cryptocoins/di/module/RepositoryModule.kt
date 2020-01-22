package com.andyshon.cryptocoins.di.module

import com.andyshon.cryptocoins.data.model.CoinService
import com.andyshon.cryptocoins.data.repository.Repository
import com.andyshon.cryptocoins.data.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providesRepository(api: CoinService): Repository {
        return RepositoryImpl(api)
    }
}