package com.andyshon.cryptocoins.ui.screens.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.andyshon.cryptocoins.R
import com.andyshon.cryptocoins.component
import com.andyshon.cryptocoins.data.entity.Coin
import com.andyshon.cryptocoins.ui.adapters.MainAdapter
import com.andyshon.cryptocoins.ui.base.recycler.ItemClickListener
import com.andyshon.cryptocoins.utils.extensions.hide
import com.andyshon.cryptocoins.utils.extensions.show
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    @Inject lateinit var factory: MainViewModelFactory
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        component.inject(this)

        viewModel = ViewModelProviders.of(this, factory)[MainViewModel::class.java]
        viewModel.loadItems()
        observeState()

        setupSwipeToRefresh()
        setupList()
    }

    private fun setupSwipeToRefresh() {
        swipeContainer.setOnRefreshListener {
            viewModel.loadItems()
        }
        swipeContainer.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )
    }

    private fun setupList() {
        adapter = MainAdapter(viewModel.coins, setClickListener())
        list.adapter = adapter
    }

    private fun setClickListener(): ItemClickListener<Coin> {
        return object : ItemClickListener<Coin> {
            override fun onItemClick(view: View, pos: Int, item: Coin) {
                toast(item.name.plus(" - ${item.quote.usd.price}"))
            }
        }
    }

    private fun observeState() {
        viewModel.state.observe(this@MainActivity, Observer<MainState>{ state ->
            when (state) {
                is MainState.DefaultState -> {
                    progressBar.hide()
                }
                is MainState.LoadingState -> {
                    swipeContainer.isRefreshing = false
                    progressBar.show()
                }
                is MainState.LoadedState -> {
                    progressBar.hide()
                    adapter.notifyDataSetChanged()
                }
                is MainState.ErrorState<*> -> {
                    when (state.message) {
                        is Int -> toast(getString(state.message))
                        is String -> toast(state.message)
                    }
                }
            }
        })
    }
}
