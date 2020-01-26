package com.andyshon.cryptocoins.di.module

import android.app.Application
import androidx.room.Room
import com.andyshon.cryptocoins.data.model.CoinService
import com.andyshon.cryptocoins.data.repository.Repository
import com.andyshon.cryptocoins.data.repository.RepositoryImpl
import com.andyshon.cryptocoins.data.repository.local.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule(private val application: Application) {

    private var appDatabase: AppDatabase =
        Room.databaseBuilder(application, AppDatabase::class.java, "coin-db").build()


    @Singleton
    @Provides
    fun provideRoomDatabase(): AppDatabase {
        return appDatabase
    }

    @Provides
    @Singleton
    fun providesRepository(api: CoinService): Repository {
        return RepositoryImpl(api, appDatabase.coinsDao(), application)
    }

    @Singleton
    @Provides
    fun provideCoinsDAO(appDatabase: AppDatabase) = appDatabase.coinsDao()
}