package com.miguelangelboti.stocks

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View.INVISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commitNow
import com.miguelangelboti.stocks.ui.add.AddOrderActivity
import com.miguelangelboti.stocks.ui.dashboard.DashboardFragment
import com.miguelangelboti.stocks.ui.stocks.StocksFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.bottomNavigation
import kotlinx.android.synthetic.main.activity_main.fab

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            showStocksFragment()
            fab.visibility = INVISIBLE
        }

        bottomNavigation.setOnNavigationItemSelectedListener(::onNavigationItemSelected)
        fab.setOnClickListener { startActivity(Intent(this, AddOrderActivity::class.java)) }
    }

    private fun onNavigationItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.item1 -> {
            showStocksFragment()
            true
        }
        R.id.item2 -> {
            showDashboardFragment()
            true
        }
        else -> false
    }

    private fun showStocksFragment() = with(supportFragmentManager) {
        fab.hide()
        findFragmentByTag(StocksFragment.TAG)?.let { return }
        commitNow { replace(R.id.container, StocksFragment.newInstance(), StocksFragment.TAG) }
    }

    private fun showDashboardFragment() = with(supportFragmentManager) {
        fab.show()
        findFragmentByTag(DashboardFragment.TAG)?.let { return }
        commitNow { replace(R.id.container, DashboardFragment.newInstance(), DashboardFragment.TAG) }
    }
}
