package com.andyshon.cryptocoins.ui.base.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andyshon.cryptocoins.data.repository.Repository
import com.andyshon.cryptocoins.ui.screens.coinDetail.CoinDetailViewModel
import com.andyshon.cryptocoins.ui.screens.coinsList.MainViewModel
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) ->
                    MainViewModel(repository)
                isAssignableFrom(CoinDetailViewModel::class.java) ->
                    CoinDetailViewModel()
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}