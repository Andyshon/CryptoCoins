package com.andyshon.cryptocoins.ui.screens.coinsList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andyshon.cryptocoins.Constants
import com.andyshon.cryptocoins.data.entity.Coin
import com.andyshon.cryptocoins.data.model.CoinService
import com.andyshon.cryptocoins.data.repository.Repository
import com.andyshon.cryptocoins.utils.SingleLiveEvent
import com.andyshon.cryptocoins.utils.extensions.applySchedulers
import com.andyshon.cryptocoins.utils.extensions.default
import io.reactivex.disposables.Disposable
import javax.inject.Inject

sealed class CoinsListState {
    object Default: CoinsListState()
    object Loading: CoinsListState()
    object HasData: CoinsListState()
    object NoData: CoinsListState()
    data class Error<T>(val message: T): CoinsListState()
}

sealed class MainToolbarState {
    data class HasTitle(val title: String): MainToolbarState()
}

sealed class Command {
    data class OpenCoinDetails(val coin: Coin): Command()
}

class MainViewModel(private val repository: Repository) : ViewModel() {

    @Inject lateinit var service: CoinService

    private lateinit var subscription: Disposable

    val state = MutableLiveData<CoinsListState>().default(initialValue = CoinsListState.Default)
    val toolbarState = MutableLiveData<MainToolbarState>()
    val command: SingleLiveEvent<Command> = SingleLiveEvent()

    val coins = arrayListOf<Coin>()

    fun showCoinDetail(coin: Coin) {
        command.postValue(Command.OpenCoinDetails(coin = coin))
    }

    fun setToolbar(title: String) {
        toolbarState.postValue(
            MainToolbarState.HasTitle(
                title = title
            )
        )
    }

    fun loadCoins() {
        state.postValue(CoinsListState.Loading)
        subscription = repository.getCoins()
            .applySchedulers()
            .doOnSubscribe {
                coins.clear()
            }
            .flatMapIterable {it}
            .take(Constants.FETCH_COINS_SIZE)
            .subscribe({
                coins.add(it)
            }, {
                state.postValue(CoinsListState.Error(it))
            }, {
                if (coins.isNotEmpty())
                    state.postValue(CoinsListState.HasData)
                else
                    state.postValue(CoinsListState.NoData)
            })
    }

    override fun onCleared() {
        super.onCleared()
        if (subscription.isDisposed.not())
            subscription.dispose()
    }
}