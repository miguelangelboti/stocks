package com.miguelangelboti.stocks.ui.stocks

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter
import com.miguelangelboti.stocks.R
import com.miguelangelboti.stocks.R.layout
import com.miguelangelboti.stocks.entities.Stock
import com.miguelangelboti.stocks.ui.stocks.StocksAdapter.StocksViewHolder
import com.miguelangelboti.stocks.utils.extensions.setStockIcon
import kotlinx.android.synthetic.main.item_order.view.swipeForegroundLayout

class StocksAdapter internal constructor(
    context: Context,
    private val listener: (Stock) -> Unit
) : RecyclerSwipeAdapter<StocksViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var data = emptyList<Stock>()

    override fun getItemCount() = data.size
    override fun getItemId(position: Int) = position.toLong()
    override fun getSwipeLayoutResourceId(position: Int) = R.id.swipeLayout

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StocksViewHolder {
        val itemView = inflater.inflate(layout.item_stock, parent, false)
        return StocksViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StocksViewHolder, position: Int) = with(holder) {
        val current = data[position]
        imageView.setStockIcon(current)
        symbolTextView.text = current.symbol
        nameTextView.text = current.name
        priceTextView.text = current.price.toString()
        swipeLayout.showMode = SwipeLayout.ShowMode.LayDown
        setListeners(current)
        mItemManger.bindView(itemView, position)
    }

    internal fun setData(data: List<Stock>) {
        this.data = data
        notifyDataSetChanged()
    }

    private fun StocksViewHolder.setListeners(current: Stock) {
        swipeLayout.swipeForegroundLayout.setOnClickListener { listener(current) }
        swipeLayout.swipeForegroundLayout.setOnLongClickListener {
            swipeLayout.open()
            true
        }
    }

    class StocksViewHolder(itemView: View) : ViewHolder(itemView) {
        val swipeLayout: SwipeLayout = itemView.findViewById(R.id.swipeLayout)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val symbolTextView: TextView = itemView.findViewById(R.id.symbolTextView)
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)
    }
}
