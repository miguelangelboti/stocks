package com.miguelangelboti.stocks.entities

import com.miguelangelboti.stocks.utils.extensions.sumByFloat

data class Stock(
    val id: Int = 0,
    val symbol: String,
    val name: String,
    val region: String,
    val marketOpen: String,
    val marketClose: String,
    val timeZone: String,
    val currency: String,
    val price: Float,
    val priceOpen: Float,
    val priceDate: String,
    val icon: String,
    val orders: List<Order> = emptyList()
)

fun Stock.getProfitability(): Float {
    return orders.sumByFloat { it.getProfitability(price) }
}
