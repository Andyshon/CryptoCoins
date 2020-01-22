package com.andyshon.cryptocoins.di.module

import com.andyshon.cryptocoins.data.repository.Repository
import com.andyshon.cryptocoins.ui.screens.main.MainViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @Provides
    fun providesMainViewModelFactory(repository: Repository): MainViewModelFactory {
        return MainViewModelFactory(repository)
    }
}