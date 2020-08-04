package com.miguelangelboti.stocks.utils.extensions

import android.widget.ImageView
import com.miguelangelboti.stocks.entities.Stock
import kotlinx.android.synthetic.main.item_stock.view.imageView

fun ImageView.setStockIcon(stock: Stock) {
    val name = "ic_${stock.icon}"
    val id = context.resources.getIdentifier(name, "drawable", context.packageName)
    imageView.setImageResource(id)
}
