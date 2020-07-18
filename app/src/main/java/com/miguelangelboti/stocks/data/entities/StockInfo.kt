package com.miguelangelboti.stocks.data.entities

import com.miguelangelboti.stocks.entities.Stock
import com.miguelangelboti.stocks.utils.getUtcDatetimeAsString

class StockInfo(
    val symbol: String,
    val name: String,
    val currency: String,
    val price: Float,
    val priceOpen: Float,
    val timeZone: String,
    val region: String,
    val marketOpen: String,
    val marketClose: String,
    val priceDate: String
)

fun StockInfo.toDomain(): Stock {
    return Stock(
        symbol = symbol,
        name = name,
        currency = currency,
        price = price,
        priceOpen = priceOpen,
        timeZone = timeZone,
        region = region,
        marketOpen = marketOpen,
        marketClose = marketClose,
        priceDate = getUtcDatetimeAsString()
    )
}
