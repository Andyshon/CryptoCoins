package com.andyshon.cryptocoins.ui.screens.coinsList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.andyshon.cryptocoins.R
import com.andyshon.cryptocoins.component
import com.andyshon.cryptocoins.data.entity.Coin
import com.andyshon.cryptocoins.ui.adapters.MainAdapter
import com.andyshon.cryptocoins.ui.base.viewModel.ViewModelFactory
import com.andyshon.cryptocoins.ui.base.recycler.ItemClickListener
import com.andyshon.cryptocoins.utils.navigation.Router
import kotlinx.android.synthetic.main.fragment_coins_list.*
import org.jetbrains.anko.support.v4.toast

import javax.inject.Inject

class CoinsListFragment : Fragment() {

    companion object {
        private const val mTitle = "List"

        fun route(): Router.FragmentTransactionBuilder {
            return Router.FragmentTransactionBuilder(CoinsListFragment())
                .setTransactionType(Router.TRANSACTION_REPLACE)
        }
    }

    private var viewModel: MainViewModel? = null
    @Inject lateinit var factory: ViewModelFactory
    private lateinit var adapter: MainAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_coins_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        component.inject(this)

        activity?.let {
            viewModel = ViewModelProviders.of(it, factory)[MainViewModel::class.java]
            viewModel?.setToolbar(mTitle)
            viewModel?.loadItems()
        }
        setupList()
        observeState()
        setupSwipeToRefresh()
    }

    private fun observeState() {
        activity?.let {
            viewModel?.state?.observe(this@CoinsListFragment, Observer<CoinsListState>{ state ->
                when (state) {
                    is CoinsListState.Default -> { swipeContainer.isRefreshing = false }
                    is CoinsListState.Loading -> { swipeContainer.isRefreshing = true }
                    is CoinsListState.HasData -> { swipeContainer.isRefreshing = false; adapter.notifyDataSetChanged() }
                    is CoinsListState.NoData -> { swipeContainer.isRefreshing = false; toast("No coins") }
                    is CoinsListState.Error<*> -> { swipeContainer.isRefreshing = false
                        when (state.message) {
                            is Int -> toast(getString(state.message))
                            is String -> toast(state.message)
                        }
                    }
                }
            })
        }
    }

    private fun setupList() {
        adapter = MainAdapter(viewModel?.coins?: arrayListOf(), setClickListener())
        list.adapter = adapter
    }

    private fun setClickListener(): ItemClickListener<Coin> {
        return object : ItemClickListener<Coin> {
            override fun onItemClick(view: View, pos: Int, item: Coin) {
                viewModel?.showCoinDetail(item)
            }
        }
    }

    private fun setupSwipeToRefresh() {
        swipeContainer.setOnRefreshListener { viewModel?.loadItems() }
        swipeContainer.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )
    }
}
