package com.andyshon.cryptocoins.di.module

import com.andyshon.cryptocoins.data.repository.Repository
import com.andyshon.cryptocoins.ui.base.viewModel.ViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @Provides
    fun providesViewModelFactory(repository: Repository): ViewModelFactory {
        return ViewModelFactory(repository)
    }
}