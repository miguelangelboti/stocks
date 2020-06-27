package com.miguelangelboti.stocks.entities

data class OrderRequest(
    val symbol: String,
    val stocks: Float,
    val price: Float
)
