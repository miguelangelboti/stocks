package com.miguelangelboti.stocks.data.network.alphavantage.results

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

fun SearchSymbolResult.toDomain(): List<Stock> {
    return matches.map { it.toDomain() }
}

fun SearchSymbolResult.toDomain(symbol: String): Stock? {
    return matches.find { it.symbol == symbol }?.toDomain()
}

private fun Match.toDomain(): Stock {
    return Stock(
        symbol = symbol,
        name = name,
        region = region
    )
}
