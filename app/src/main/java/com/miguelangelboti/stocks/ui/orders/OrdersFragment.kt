package com.miguelangelboti.stocks.ui.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import com.miguelangelboti.stocks.R
import com.miguelangelboti.stocks.entities.Stock
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_orders.recyclerView
import kotlinx.android.synthetic.main.fragment_orders.swipeRefreshLayout

@AndroidEntryPoint
class OrdersFragment : Fragment() {

    companion object {
        val TAG = OrdersFragment::class.java.simpleName
        private const val BUNDLE_KEY_STOCK_ID = "BUNDLE_KEY_STOCK_ID"
        fun newInstance(stockId: Int) = OrdersFragment().apply {
            arguments = bundleOf(Pair(BUNDLE_KEY_STOCK_ID, stockId))
        }
    }

    private val viewModel by viewModels<OrdersViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_orders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.addItemDecoration(DividerItemDecoration(context, VERTICAL))
        swipeRefreshLayout.setOnRefreshListener { viewModel.refresh(getStockId()) }
        viewModel.stopRefresh.observe(viewLifecycleOwner) { swipeRefreshLayout.isRefreshing = false }
        viewModel.stock.observe(viewLifecycleOwner) { setStock(it) }
        viewModel.init(getStockId())
    }

    private fun getStockId(): Int {
        return requireArguments().getInt(BUNDLE_KEY_STOCK_ID)
    }

    private fun setStock(stock: Stock) {
        val context = context ?: return
        val adapter = (recyclerView.adapter as OrdersAdapter?)
        if (adapter != null) {
            adapter.setStock(stock)
        } else {
            recyclerView.adapter = OrdersAdapter(context, stock)
        }
    }
}
