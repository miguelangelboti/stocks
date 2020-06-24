package com.miguelangelboti.stocks.data.network.alphavantage.results

import com.miguelangelboti.stocks.entities.Stock
import com.squareup.moshi.Json

data class SearchSymbolResult(@field:Json(name = "bestMatches") val matches: List<Match>)

data class Match(
    @field:Json(name = "1. symbol") val symbol: String,
    @field:Json(name = "2. name") val name: String,
    @field:Json(name = "4. region") val region: String
)

fun SearchSymbolResult.toDomain(): List<Stock> {
    return matches.map { Stock(it.symbol, it.name, it.region) }
}
