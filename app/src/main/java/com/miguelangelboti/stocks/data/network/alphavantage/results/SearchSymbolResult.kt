package com.miguelangelboti.stocks.data.network.alphavantage.results

import com.miguelangelboti.stocks.data.entities.StockInfo
import com.miguelangelboti.stocks.entities.Stock
import com.squareup.moshi.Json

data class SearchSymbolResult(@field:Json(name = "bestMatches") val matches: List<Match>)

data class Match(
    @field:Json(name = "1. symbol") val symbol: String,
    @field:Json(name = "2. name") val name: String,
    @field:Json(name = "4. region") val region: String,
    @field:Json(name = "5. marketOpen") val marketOpen: String,
    @field:Json(name = "6. marketClose") val marketClose: String,
    @field:Json(name = "7. timezone") val timezone: String,
    @field:Json(name = "8. currency") val currency: String
)

fun SearchSymbolResult.toDomain(): List<String> {
    return matches.map { it.symbol }
}

fun SearchSymbolResult.toDomain(stockInfo: StockInfo): Stock? {
    return matches
        .find { it.symbol == stockInfo.symbol }
        ?.run {
            Stock(
                symbol = symbol,
                name = name,
                region = region,
                marketOpen = marketOpen,
                marketClose = marketClose,
                timezone = timezone,
                currency = currency,
                price = stockInfo.price,
                priceOpen = stockInfo.priceOpen,
                priceDate = stockInfo.priceDate
            )
        }
}
