package com.miguelangelboti.stocks.ui.orders

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter
import com.miguelangelboti.stocks.R
import com.miguelangelboti.stocks.R.layout
import com.miguelangelboti.stocks.entities.Order
import com.miguelangelboti.stocks.entities.Stock
import com.miguelangelboti.stocks.entities.getProfitability
import com.miguelangelboti.stocks.entities.hasPositiveProfitability
import com.miguelangelboti.stocks.ui.orders.OrdersAdapter.OrderViewHolder
import com.miguelangelboti.stocks.utils.getCurrencySymbol
import kotlinx.android.synthetic.main.item_order.view.swipeForegroundLayout

class OrdersAdapter internal constructor(
    private val context: Context,
    private var stock: Stock
) : RecyclerSwipeAdapter<OrderViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getItemCount() = stock.orders.size
    override fun getItemId(position: Int) = position.toLong()
    override fun getSwipeLayoutResourceId(position: Int) = R.id.swipeLayout

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val itemView = inflater.inflate(layout.item_order, parent, false)
        return OrderViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) = with(holder) {
        val order = stock.orders[position]
        val colorResId = if (order.hasPositiveProfitability(stock.price)) R.color.green else R.color.red
        val color = ContextCompat.getColor(context, colorResId)
        stocksTextView.text = context.getString(R.string.titles, order.stocks.toString())
        priceTextView.text = order.price.toString()
        profitabilityTextView.text = order.getProfitability(stock.price)
        profitabilityTextView.setTextColor(color)
        currencyTextView.text = getCurrencySymbol(stock.currency)
        currencyTextView.setTextColor(color)
        swipeLayout.showMode = SwipeLayout.ShowMode.LayDown
        setListeners(order)
        mItemManger.bindView(itemView, position)
    }

    internal fun setStock(stock: Stock) {
        this.stock = stock
        notifyDataSetChanged()
    }

    private fun OrderViewHolder.setListeners(order: Order) {
        swipeLayout.swipeForegroundLayout.setOnLongClickListener {
            swipeLayout.open()
            true
        }
        swipeLayout.swipeForegroundLayout.setOnClickListener {
            Toast.makeText(itemView.context, order.price.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    inner class OrderViewHolder(itemView: View) : ViewHolder(itemView) {
        val swipeLayout: SwipeLayout = itemView.findViewById(R.id.swipeLayout)
        val stocksTextView: TextView = itemView.findViewById(R.id.stocksTextView)
        val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)
        val profitabilityTextView: TextView = itemView.findViewById(R.id.profitabilityTextView)
        val currencyTextView: TextView = itemView.findViewById(R.id.currencyTextView)
    }
}
