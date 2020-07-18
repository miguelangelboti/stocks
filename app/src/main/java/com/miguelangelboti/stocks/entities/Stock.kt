package com.miguelangelboti.stocks.entities

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
    val orders: List<Order> = emptyList()
)
