package com.miguelangelboti.stocks.entities

data class Order(
    val id: Int? = null,
    val stock: Stock,
    val stocks: Float,
    val price: Float
) {
    constructor(request: OrderRequest, stock: Stock) : this(
        stock = stock,
        stocks = request.stocks,
        price = request.price
    )
}
