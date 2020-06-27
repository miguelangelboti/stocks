package com.miguelangelboti.stocks.ui.orders

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commitNow
import com.miguelangelboti.stocks.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersActivity : AppCompatActivity() {

    companion object {
        const val BUNDLE_KEY_STOCK_ID = "BUNDLE_KEY_STOCK_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)
        val stockId = intent.getIntExtra(BUNDLE_KEY_STOCK_ID, 0)
        if (savedInstanceState == null) {
            supportFragmentManager.commitNow {
                replace(R.id.container, OrdersFragment.newInstance(stockId), OrdersFragment.TAG)
            }
        }
    }
}
