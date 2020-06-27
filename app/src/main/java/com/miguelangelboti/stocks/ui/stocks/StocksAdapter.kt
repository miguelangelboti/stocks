package com.miguelangelboti.stocks.ui.stocks

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter
import com.miguelangelboti.stocks.R
import com.miguelangelboti.stocks.R.layout
import com.miguelangelboti.stocks.entities.Order
import com.miguelangelboti.stocks.ui.stocks.StocksAdapter.StocksViewHolder
import kotlinx.android.synthetic.main.item_recyclerview.view.swipeForegroundLayout

class StocksAdapter internal constructor(context: Context) : RecyclerSwipeAdapter<StocksViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var data = emptyList<Order>()

    override fun getItemCount() = data.size
    override fun getItemId(position: Int) = position.toLong()
    override fun getSwipeLayoutResourceId(position: Int) = R.id.swipeLayout

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StocksViewHolder {
        val itemView = inflater.inflate(layout.item_recyclerview, parent, false)
        return StocksViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StocksViewHolder, position: Int) {
        val current = data[position]
        holder.symbolTextView.text = current.stock.symbol
//        holder.nameTextView.text = current.name
        holder.priceTextView.text = current.stock.price.toString()
        holder.swipeLayout.showMode = SwipeLayout.ShowMode.LayDown
        setListeners(holder, current)
        mItemManger.bindView(holder.itemView, position)
    }

    internal fun setData(data: List<Order>) {
        this.data = data
        notifyDataSetChanged()
    }

    private fun setListeners(holder: StocksViewHolder, current: Order) {
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

    inner class StocksViewHolder(itemView: View) : ViewHolder(itemView) {
        val swipeLayout: SwipeLayout = itemView.findViewById(R.id.swipeLayout)
        val symbolTextView: TextView = itemView.findViewById(R.id.symbolTextView)
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)
    }
}
