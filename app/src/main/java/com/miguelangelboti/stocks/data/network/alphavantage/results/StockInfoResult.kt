package com.miguelangelboti.stocks.data.network.alphavantage.results

import com.miguelangelboti.stocks.data.entities.StockInfo
import com.miguelangelboti.stocks.utils.getUtcDatetimeAsString
import com.squareup.moshi.Json

data class StockInfoResult(@field:Json(name = "Global Quote") val quote: Quote)

data class Quote(
    @field:Json(name = "01. symbol") val symbol: String,
    @field:Json(name = "02. open") val priceOpen: Float,
    @field:Json(name = "05. price") val price: Float
)

fun StockInfoResult.toDomain(): StockInfo = with(quote) {
    return StockInfo(
        symbol = symbol,
        price = price,
        priceOpen = priceOpen,
        priceDate = getUtcDatetimeAsString()
    )
}
