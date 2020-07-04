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

fun Order.hasPositiveProfitability(price: Float): Boolean {
    return getProfitability(price)?.let { it >= 0 } ?: false
}

fun Order.getProfitability(price: Float, decimals: Int = 2): String {
    return getProfitability(price)?.let { "%.${decimals}f".format(it) } ?: ""
}

private fun Order.getProfitability(price: Float): Float? {
    return price * stocks - stocks * this.price
}
