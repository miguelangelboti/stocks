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

fun Order.hasPositiveProfitability(): Boolean {
    return getProfitability()?.let { it >= 0 } ?: false
}

fun Order.getProfitability(decimals: Int = 2): String {
    return getProfitability()?.let { "%.${decimals}f".format(it) } ?: ""
}

private fun Order.getProfitability(): Float? {
    return stock.price?.let { it * stocks - stocks * price }
}
