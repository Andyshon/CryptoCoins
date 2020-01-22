package com.andyshon.cryptocoins.di.module

import android.content.Context
import com.andyshon.cryptocoins.App
import com.andyshon.cryptocoins.di.qualifier.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {

    @Provides
    @Singleton
    @ApplicationContext
    fun provideApplicationContext(): Context {
        return app
    }

    @Provides
    @Singleton
    fun provideApplication(): App {
        return app
    }

}