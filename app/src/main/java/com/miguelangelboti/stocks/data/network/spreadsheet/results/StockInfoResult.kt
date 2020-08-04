package com.miguelangelboti.stocks.data.network.spreadsheet.results

import com.miguelangelboti.stocks.entities.Stock
import com.miguelangelboti.stocks.utils.getUtcDatetimeAsString
import com.squareup.moshi.Json

data class StockInfoResult(
    @field:Json(name = "symbol") val symbol: String,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "currency") val currency: String,
    @field:Json(name = "price") val price: Float,
    @field:Json(name = "priceOpen") val priceOpen: Float,
    @field:Json(name = "timeZone") val timeZone: String,
    @field:Json(name = "region") val region: String,
    @field:Json(name = "marketOpen") val marketOpen: String,
    @field:Json(name = "marketClose") val marketClose: String,
    @field:Json(name = "icon") val icon: String
)

fun StockInfoResult.toDomain(): Stock {
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
        icon = icon,
        priceDate = getUtcDatetimeAsString()
    )
}
