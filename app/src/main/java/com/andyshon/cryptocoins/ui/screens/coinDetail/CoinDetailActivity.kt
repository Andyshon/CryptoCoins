package com.andyshon.cryptocoins.ui.screens.coinDetail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.andyshon.cryptocoins.R
import com.andyshon.cryptocoins.component
import com.andyshon.cryptocoins.data.entity.Coin
import kotlinx.android.synthetic.main.activity_coin_detail.*

class CoinDetailActivity : AppCompatActivity() {

    companion object {
        fun startActivity(context: Context, coin: Coin) {
            val intent = Intent(context, CoinDetailActivity::class.java).apply {
                putExtra("Coin", coin)
            }
            context.startActivity(intent)
        }
    }

    private lateinit var viewModel: CoinDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_detail)

        component.inject(this)
        viewModel = ViewModelProviders.of(this)[CoinDetailViewModel::class.java]
        observeState()
    }

    private fun observeState() {
        viewModel.state.observe(this, Observer<CoinDetailState> { state ->
            when (state) {
                is CoinDetailState.SetUI -> {
                    intent.extras?.getParcelable<Coin>("Coin")?.let {
                        viewModel.coin = it
                        setUI()
                    }
                }
            }
        })
    }

    private fun setUI() {
        tvPrice.text = viewModel.coin.quote.usd.price.toString()
        tvHr1.text = viewModel.coin.quote.usd.percentChange1h.toString()
    }
}
