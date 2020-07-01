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
import com.miguelangelboti.stocks.entities.getProfitability
import com.miguelangelboti.stocks.entities.hasPositiveProfitability
import com.miguelangelboti.stocks.ui.orders.OrdersAdapter.OrderViewHolder
import com.miguelangelboti.stocks.utils.getCurrencySymbol
import kotlinx.android.synthetic.main.item_order.view.swipeForegroundLayout

class OrdersAdapter internal constructor(
    private val context: Context
) : RecyclerSwipeAdapter<OrderViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var data = emptyList<Order>()

    override fun getItemCount() = data.size
    override fun getItemId(position: Int) = position.toLong()
    override fun getSwipeLayoutResourceId(position: Int) = R.id.swipeLayout

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val itemView = inflater.inflate(layout.item_order, parent, false)
        return OrderViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val current = data[position]
        val profitabilityColor = if (current.hasPositiveProfitability()) R.color.green else R.color.red
        holder.stocksTextView.text = context.getString(R.string.titles, current.stocks.toString())
        holder.priceTextView.text = current.price.toString()
        holder.profitabilityTextView.text = current.getProfitability()
        holder.profitabilityTextView.setTextColor(ContextCompat.getColor(context, profitabilityColor))
        holder.currencyTextView.text = getCurrencySymbol(current.stock.currency)
        holder.swipeLayout.showMode = SwipeLayout.ShowMode.LayDown
        setListeners(holder, current)
        mItemManger.bindView(holder.itemView, position)
    }

    internal fun setData(data: List<Order>) {
        this.data = data
        notifyDataSetChanged()
    }

    private fun setListeners(holder: OrderViewHolder, current: Order) {
        holder.swipeLayout.swipeForegroundLayout.setOnLongClickListener {
            holder.swipeLayout.open()
            true
        }
        holder.swipeLayout.swipeForegroundLayout.setOnClickListener {
            Toast.makeText(
                holder.itemView.context,
                current.stock.symbol,
                Toast.LENGTH_SHORT
            ).show()
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
