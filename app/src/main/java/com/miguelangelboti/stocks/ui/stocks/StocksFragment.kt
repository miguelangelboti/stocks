package com.miguelangelboti.stocks.ui.stocks

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import com.miguelangelboti.stocks.R
import com.miguelangelboti.stocks.entities.Stock
import com.miguelangelboti.stocks.ui.orders.OrdersActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_orders.recyclerView
import kotlinx.android.synthetic.main.fragment_orders.swipeRefreshLayout

@AndroidEntryPoint
class StocksFragment : Fragment() {

    companion object {
        val TAG = StocksFragment::class.java.simpleName
        fun newInstance() = StocksFragment()
    }

    private val viewModel by viewModels<StocksViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_orders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = StocksAdapter(view.context, ::onItemClickListener)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.addItemDecoration(DividerItemDecoration(context, VERTICAL))
        swipeRefreshLayout.setOnRefreshListener { viewModel.refresh() }
        viewModel.stopRefresh.observe(viewLifecycleOwner) { swipeRefreshLayout.isRefreshing = false }
        viewModel.stocks.observe(viewLifecycleOwner) { adapter.setData(it) }
    }

    private fun onItemClickListener(stock: Stock) {
        val context = context ?: return
        startActivity(Intent(context, OrdersActivity::class.java).apply {
            putExtra(OrdersActivity.BUNDLE_KEY_STOCK_ID, stock.id)
        })
    }
}
