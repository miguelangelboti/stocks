package com.miguelangelboti.stocks.ui.stocks

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
import com.miguelangelboti.stocks.ui.orders.OrdersFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_orders.recyclerView
import kotlinx.android.synthetic.main.fragment_orders.swipeRefreshLayout

@AndroidEntryPoint
class StocksFragment : Fragment() {

    companion object {
        val TAG = OrdersFragment::class.java.simpleName
        fun newInstance() = OrdersFragment()
    }

    private val viewModel by viewModels<StocksViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_orders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = StocksAdapter(view.context)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.addItemDecoration(DividerItemDecoration(context, VERTICAL))
        swipeRefreshLayout.setOnRefreshListener { viewModel.refresh() }
        viewModel.stopRefresh.observe(viewLifecycleOwner) { swipeRefreshLayout.isRefreshing = false }
        viewModel.orders.observe(viewLifecycleOwner) { adapter.setData(it) }
    }
}
