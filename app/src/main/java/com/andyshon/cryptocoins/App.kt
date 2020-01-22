package com.andyshon.cryptocoins

import android.app.Application
import com.andyshon.cryptocoins.di.component.AppComponent
import com.andyshon.cryptocoins.di.component.DaggerAppComponent
import com.andyshon.cryptocoins.di.module.AppModule
import com.andyshon.cryptocoins.di.module.RepositoryModule
import com.andyshon.cryptocoins.di.module.ViewModelModule
import com.facebook.stetho.Stetho
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        initDagger()
    }

    private fun initDagger() {
        component = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .viewModelModule(ViewModelModule())
            .repositoryModule(RepositoryModule())
            .build()
    }

}

lateinit var component: AppComponent
