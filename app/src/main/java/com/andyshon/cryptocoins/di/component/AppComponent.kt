package com.andyshon.cryptocoins.di.component

import com.andyshon.cryptocoins.di.module.*
import com.andyshon.cryptocoins.ui.screens.main.MainActivity
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
}