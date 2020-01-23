package com.andyshon.cryptocoins.ui.screens.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.andyshon.cryptocoins.R
import com.andyshon.cryptocoins.component
import com.andyshon.cryptocoins.ui.base.viewModel.ViewModelFactory
import com.andyshon.cryptocoins.ui.screens.coinDetail.CoinDetailActivity
import com.andyshon.cryptocoins.ui.screens.coinsList.CoinsListFragment
import com.andyshon.cryptocoins.ui.screens.coinsList.Command
import com.andyshon.cryptocoins.ui.screens.coinsList.MainToolbarState
import com.andyshon.cryptocoins.ui.screens.coinsList.MainViewModel
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*
import org.jetbrains.anko.toast
import javax.inject.Inject

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var toggle: ActionBarDrawerToggle
    @Inject lateinit var factory: ViewModelFactory
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        component.inject(this)
        viewModel = ViewModelProviders.of(this, factory)[MainViewModel::class.java]
        observeState()

        showDefaultFragment()
        initUI()
    }

    private fun observeState() {
        viewModel.toolbarState.observe(this@MainActivity, Observer<MainToolbarState> { state ->
            when(state) {
                is MainToolbarState.HasTitle -> {
                    toolbar.toolbarTitle.text = state.title
                }
            }
        })
        viewModel.command.observe(this@MainActivity, Observer { state ->
            when (state) {
                is Command.OpenCoinDetails -> {
                    CoinDetailActivity.startActivity(this, state.coin)
                }
            }
        })
    }

    private fun initUI() {
        toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        toggle.drawerArrowDrawable.color = ContextCompat.getColor(this, R.color.colorWhite)
        drawer_layout.addDrawerListener(toggle)

        navView.setNavigationItemSelectedListener(this)
        toggle.syncState()
    }

    private fun showDefaultFragment() {
        CoinsListFragment.route().applyTransaction(supportFragmentManager)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_item_list -> {
                CoinsListFragment.route().applyTransaction(supportFragmentManager)
            }
            R.id.nav_item_about_us -> { toast("About us") }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}