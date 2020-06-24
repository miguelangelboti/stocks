package com.miguelangelboti.stocks.entities

data class Stock(
    val symbol: String,
    val name: String? = null,
    val region: String? = null,
    val price: Price? = null
)
