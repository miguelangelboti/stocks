package com.miguelangelboti.stocks

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commitNow
import com.miguelangelboti.stocks.ui.add.AddOrderActivity
import com.miguelangelboti.stocks.ui.dashboard.DashboardFragment
import com.miguelangelboti.stocks.ui.orders.OrdersFragment
import kotlinx.android.synthetic.main.activity_main.bottomNavigation
import kotlinx.android.synthetic.main.activity_main.fab

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            bottomNavigation.selectedItemId = R.id.item2
            showOrdersFragment()
        }

        bottomNavigation.setOnNavigationItemSelectedListener(::onNavigationItemSelected)
        fab.setOnClickListener { startActivity(Intent(this, AddOrderActivity::class.java)) }
    }

    private fun onNavigationItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.item1 -> {
            fab.hide()
            showDashboardFragment()
            true
        }
        R.id.item2 -> {
            fab.show()
            showOrdersFragment()
            true
        }
        else -> false
    }

    private fun showDashboardFragment() = with(supportFragmentManager) {
        findFragmentByTag(DashboardFragment.TAG)?.let { return }
        commitNow { replace(R.id.container, DashboardFragment.newInstance(), DashboardFragment.TAG) }
    }

    private fun showOrdersFragment() = with(supportFragmentManager) {
        findFragmentByTag(OrdersFragment.TAG)?.let { return }
        commitNow { replace(R.id.container, OrdersFragment.newInstance(), OrdersFragment.TAG) }
    }
}
