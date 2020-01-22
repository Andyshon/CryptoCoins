package com.andyshon.cryptocoins.ui.screens.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andyshon.cryptocoins.data.entity.Coin
import com.andyshon.cryptocoins.data.model.CoinService
import com.andyshon.cryptocoins.data.repository.Repository
import com.andyshon.cryptocoins.utils.extensions.applySchedulers
import com.andyshon.cryptocoins.utils.extensions.default
import io.reactivex.disposables.Disposable
import javax.inject.Inject

sealed class MainState {
    object DefaultState: MainState()
    object LoadingState: MainState()
    object LoadedState: MainState()
    data class ErrorState<T>(val message: T): MainState()
}

class MainViewModel(private val repository: Repository) : ViewModel() {

    @Inject lateinit var service: CoinService

    private lateinit var subscription: Disposable

    val state = MutableLiveData<MainState>().default(initialValue = MainState.DefaultState)

    val coins = arrayListOf<Coin>()


    fun loadItems() {
        state.postValue(MainState.LoadingState)
        subscription = repository.getCoins()
            .applySchedulers()
            .subscribe({
                coins.addAll(it)
                state.postValue(MainState.LoadedState)
            }, {
                state.postValue(MainState.ErrorState(it))
            })
    }

    override fun onCleared() {
        super.onCleared()
        if (subscription.isDisposed.not())
            subscription.dispose()
    }
}