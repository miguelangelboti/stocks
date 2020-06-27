package com.miguelangelboti.stocks.entities

data class Stock(
    val id: Int = 0,
    val symbol: String,
    val name: String? = null,
    val region: String? = null,
    val price: Float? = null,
    val priceDate: String? = null
)
