package com.miguelangelboti.stocks.entities

data class Order(
    val id: Int? = null,
    val stocks: Float,
    val price: Float
) {
    constructor(request: OrderRequest) : this(
        stocks = request.stocks,
        price = request.price
    )
}

fun Order.hasProfits(price: Float): Boolean {
    return getProfitability(price) >= 0
}

fun Order.getProfitability(price: Float): Float {
    return price * stocks - stocks * this.price
}
