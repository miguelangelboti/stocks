package com.miguelangelboti.stocks.entities

data class Order(
    val id: Int? = null,
    val stock: Stock,
    val stocks: Float,
    val price: Float
)
